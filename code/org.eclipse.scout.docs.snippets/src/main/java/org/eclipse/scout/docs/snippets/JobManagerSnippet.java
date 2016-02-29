package org.eclipse.scout.docs.snippets;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.rt.client.context.ClientRunContext;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.exception.DefaultExceptionTranslator;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.filter.IFilter;
import org.eclipse.scout.rt.platform.job.DoneEvent;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IBlockingCondition;
import org.eclipse.scout.rt.platform.job.IDoneHandler;
import org.eclipse.scout.rt.platform.job.IExecutionSemaphore;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.IJobManager;
import org.eclipse.scout.rt.platform.job.JobInput;
import org.eclipse.scout.rt.platform.job.JobState;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.job.listener.IJobListener;
import org.eclipse.scout.rt.platform.job.listener.JobEvent;
import org.eclipse.scout.rt.platform.job.listener.JobEventType;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.job.filter.event.SessionJobEventFilter;
import org.eclipse.scout.rt.shared.job.filter.future.SessionFutureFilter;
import org.quartz.CronScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;

@SuppressWarnings("unused")
public final class JobManagerSnippet {

  // tag::IRunnable[]
  public class Work implements IRunnable {

    @Override
    public void run() throws Exception {
      // do some work
    }
  }

  // end::IRunnable[]

  // tag::Callable[]
  public class WorkWithResult implements Callable<String> {

    @Override
    public String call() throws Exception {
      // do some work
      return "result";
    }
  }

  // end::Callable[]

  // tag::CancellableWork[]
  public class CancellableWork implements IRunnable {

    @Override
    public void run() throws Exception {

      // do first chunk of operations

      if (RunMonitor.CURRENT.get().isCancelled()) {
        return;
      }

      // do next chunk of operations

      if (RunMonitor.CURRENT.get().isCancelled()) {
        return;
      }

      // do next chunk of operations
    }
  }
  // end::CancellableWork[]

  // tag::future.threadLocal[]
  public class Job implements IRunnable {

    @Override
    public void run() throws Exception {
      IFuture<?> myFuture = IFuture.CURRENT.get();
    }
  }
  // end::future.threadLocal[]

  @SuppressWarnings("null")
  void snippets() throws Exception {
    {
      // tag::jobInput.name[]
      Jobs.newInput()
          .withName("Sending emails [from={}, to={}]", "frank", "john@eclipse.org, jack@eclipse.org");
      // end::jobInput.name[]
    }

    {
      // tag::jobInput.example[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job name") // <1>
          .withRunContext(ClientRunContexts.copyCurrent()) // <2>
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(10, TimeUnit.SECONDS) // <3>
              .withSchedule(FixedDelayScheduleBuilder.repeatForever(5, TimeUnit.SECONDS))) // <4>
          .withExceptionHandling(new ExceptionHandler() { // <5>

            @Override
            public void handle(Throwable t) {
              System.err.println(t);
            }
          }, true));
      // end::jobInput.example[]
    }

    {
      // tag::jobManager.schedule1[]
      IJobManager jobManager = BEANS.get(IJobManager.class); // <1>

      jobManager.schedule(new IRunnable() { // <2>

        @Override
        public void run() throws Exception {
          // do something
        }
      }, BEANS.get(JobInput.class)); // <3>
      // end::jobManager.schedule1[]
    }

    {
      // tag::jobManager.schedule2[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput());
      // end::jobManager.schedule2[]
    }

    {
      // tag::future.cancel[]
      // Schedule a job
      IFuture<?> future = Jobs.schedule(new Work(), Jobs.newInput());

      // Cancel the job via its future
      future.cancel(false);
      // end::future.cancel[]
    }

    {
      IFuture<?> future1 = null;
      IFuture<?> future2 = null;
      IFuture<?> future3 = null;

      // tag::jobManager.cancel1[]
      Jobs.getJobManager().cancel(Jobs.newFutureFilterBuilder()
          .andMatchFuture(future1, future2, future3)
          .toFilter(), false);

      // end::jobManager.cancel1[]
    }

    {
      // tag::jobManager.cancel2[]
      Jobs.getJobManager().cancel(Jobs.newFutureFilterBuilder()
          .andMatchExecutionHint("computation")
          .andMatch(new SessionFutureFilter(ISession.CURRENT.get()))
          .toFilter(), false);
      // end::jobManager.cancel2[]
    }

    {
      // tag::futureFilterBuilder.example[]
      IFilter<IFuture<?>> filter = Jobs.newFutureFilterBuilder() // <1>
          .andMatchExecutionHint("computation") // <2>
          .andMatchNotState(JobState.PENDING) // <3>
          .andAreSingleExecuting() // <4>
          .andMatchNotFuture(IFuture.CURRENT.get()) // <5>
          .andMatchRunContext(ClientRunContext.class) // <6>
          .andMatch(new SessionFutureFilter(ISession.CURRENT.get())) // <7>
          .toFilter(); // <8>
      // end::futureFilterBuilder.example[]
    }

    {
      // tag::jobEventFilterBuilder.example[]
      IFilter<JobEvent> filter = Jobs.newEventFilterBuilder() // <1>
          .andMatchEventType(JobEventType.JOB_STATE_CHANGED) // <2>
          .andMatchState(JobState.RUNNING) // <3>
          .andMatch(new SessionJobEventFilter(ISession.CURRENT.get())) // <4>
          .andMatchExecutionHint("computation") // <5>
          .toFilter(); // <6>
      // end::jobEventFilterBuilder.example[]
    }

    {
      // tag::jobManager.registerListener[]
      Jobs.getJobManager().addListener(Jobs.newEventFilterBuilder() // <1>
          .andMatchEventType(JobEventType.JOB_STATE_CHANGED)
          .andMatchState(JobState.RUNNING)
          .andMatch(new SessionJobEventFilter(ISession.CURRENT.get()))
          .toFilter(), new IJobListener() {

            @Override
            public void changed(JobEvent event) {
              IFuture<?> future = event.getData().getFuture(); // <2>
              System.out.println("Job commences execution: " + future.getJobInput().getName());
            }
          });
      // end::jobManager.registerListener[]
    }

    {
      IFuture<?> future = null;
      // tag::future.registerListener[]
      future.addListener(Jobs.newEventFilterBuilder()
          .andMatchEventType(JobEventType.JOB_STATE_CHANGED)
          .andMatchState(JobState.RUNNING)
          .toFilter(), new IJobListener() {

            @Override
            public void changed(JobEvent event) {
              System.out.println("Job commences execution");
            }
          });
      // end::future.registerListener[]
    }

    {
      // tag::future.awaitDone[]
      IFuture<String> future = Jobs.schedule(new Callable<String>() {

        @Override
        public String call() throws Exception {
          // doing something
          return "computation result";
        }
      }, Jobs.newInput());

      // Wait until done without consuming the result
      future.awaitDone(); // <1>
      future.awaitDone(10, TimeUnit.SECONDS); // <2>

      // Wait until done and consume the result
      String result = future.awaitDoneAndGet(); // <3>
      result = future.awaitDoneAndGet(10, TimeUnit.SECONDS); // <4>

      // Wait until done, consume the result, and use a specific exception translator
      result = future.awaitDoneAndGet(DefaultExceptionTranslator.class); // <5>
      result = future.awaitDoneAndGet(10, TimeUnit.SECONDS, DefaultExceptionTranslator.class); // <6>

      // end::future.awaitDone[]
    }

    {
      IFuture<String> future = null;

      // tag::future.whenDone[]
      future.whenDone(new IDoneHandler<String>() {

        @Override
        public void onDone(DoneEvent<String> event) {
          // invoked upon entering done state.
        }
      }, ClientRunContexts.copyCurrent());

      // end::future.whenDone[]
    }

    {
      // tag::future.awaitFinished[]
      IFuture<String> future = Jobs.schedule(new Callable<String>() {

        @Override
        public String call() throws Exception {
          // doing something
          return "computation result";
        }
      }, Jobs.newInput());

      // Wait until finished
      future.awaitFinished(10, TimeUnit.SECONDS);

      // end::future.awaitFinished[]
    }

    {
      IFuture<?> future1 = null;
      IFuture<?> future2 = null;
      IFuture<?> future3 = null;

      // tag::jobManager.awaitDone[]
      // Wait for some futures
      Jobs.getJobManager().awaitDone(Jobs.newFutureFilterBuilder() // <1>
          .andMatchFuture(future1, future2, future3)
          .toFilter(), 1, TimeUnit.MINUTES);

      // Wait for all futures marked as 'reporting' jobs of the current session
      Jobs.getJobManager().awaitDone(Jobs.newFutureFilterBuilder() // <2>
          .andMatchExecutionHint("reporting")
          .andMatch(new SessionFutureFilter(ISession.CURRENT.get()))
          .toFilter(), 1, TimeUnit.MINUTES);

      // end::jobManager.awaitDone[]
    }

    {
      IFuture<?> future1 = null;
      IFuture<?> future2 = null;
      IFuture<?> future3 = null;

      // tag::jobManager.awaitFinished[]
      // Wait for some futures
      Jobs.getJobManager().awaitFinished(Jobs.newFutureFilterBuilder() // <1>
          .andMatchFuture(future1, future2, future3)
          .toFilter(), 1, TimeUnit.MINUTES);

      // Wait for all futures marked as 'reporting' jobs of the current session
      Jobs.getJobManager().awaitFinished(Jobs.newFutureFilterBuilder() // <2>
          .andMatchExecutionHint("reporting")
          .andMatch(new SessionFutureFilter(ISession.CURRENT.get()))
          .toFilter(), 1, TimeUnit.MINUTES);

      // end::jobManager.awaitFinished[]
    }

    {
      // tag::blockingCondition.example1[]
      // Schedule a long running operation.
      IFuture<?> future = Jobs.schedule(new LongRunningOperation(), Jobs.newInput());

      // Wait until done.
      future.awaitDone();
      // end::blockingCondition.example1[]
    }

    {
      // tag::blockingCondition.example2[]

      // Create a blocking condition.
      final IBlockingCondition operationCompleted = Jobs.newBlockingCondition(true);

      // Schedule a long running operation.
      IFuture<Void> future = Jobs.schedule(new LongRunningOperation(), Jobs.newInput());

      // Register done callback to unblock the condition.
      future.whenDone(new IDoneHandler<Void>() {

        @Override
        public void onDone(DoneEvent<Void> event) {
          // Let the waiting job re-acquire a permit and continue execution.
          operationCompleted.setBlocking(false);
        }
      }, null);

      // Wait until done. Thereby, the permit of the current job is released for the time while waiting.
      operationCompleted.waitForUninterruptibly();

      // end::blockingCondition.example2[]
    }

    {
      // tag::executionTrigger.examples[]
      // Schedules a delayed single executing job
      Jobs.newInput()
          .withName("job")
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(10, TimeUnit.SECONDS));

      // Schedules a repeatedly running job at a fixed rate (every hour), which ends in 24 hours
      Jobs.newInput()
          .withName("job")
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withEndIn(1, TimeUnit.DAYS)
              .withSchedule(SimpleScheduleBuilder.repeatHourlyForever()));

      // Schedules a job which runs at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday
      Jobs.newInput()
          .withName("job")
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * MON-FRI")));

      // end::executionTrigger.examples[]
    }

    {
      // tag::executionSemaphore.example[]
      IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(5); // <1>

      for (int i = 0; i < 100; i++) {
        Jobs.schedule(new IRunnable() { // <2>

          @Override
          public void run() throws Exception {
            // doing something
          }
        }, Jobs.newInput()
            .withName("job-{}", i)
            .withExecutionSemaphore(semaphore)); // <3>
      }
      // end::executionSemaphore.example[]
    }

    {
      // tag::modelJobs.example[]
      ModelJobs.schedule(new IRunnable() { // <1>

        @Override
        public void run() throws Exception {
          // doing something in model thread
        }
      }, ModelJobs.newInput(ClientRunContexts.copyCurrent()) // <2>
          .withName("Doing something in model thread"));
      // end::modelJobs.example[]
    }

    {
      // tag::modelJobs.api[]
      // Returns true if the current thread represents the model thread for the current client session. At any given time, there is only one model thread active per client session.
      ModelJobs.isModelThread();

      // Returns true if the given Future belongs to a model job.
      ModelJobs.isModelJob(IFuture.CURRENT.get());

      // Returns a builder to create a filter for future objects representing a model job.
      ModelJobs.newFutureFilterBuilder();

      // Returns a builder to create a filter for JobEvent objects originating from model jobs.
      ModelJobs.newEventFilterBuilder();

      // Instructs the job manager that the current model job is willing to temporarily yield its current model job permit. It is rarely appropriate to use this method. It may be useful for debugging or testing purposes.
      ModelJobs.yield();
      // end::modelJobs.api[]
    }

    {
      // tag::examples.scheduling.one-shot[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running once")
          .withRunContext(ClientRunContexts.copyCurrent()));
      // end::examples.scheduling.one-shot[]
    }

    {
      // tag::examples.scheduling.delayed[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running in 10 seconds")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(10, TimeUnit.SECONDS))); // delay of 10 seconds
      // end::examples.scheduling.delayed[]
    }

    {
      // tag::examples.scheduling.at-fixed-rate-infinitely[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running every minute")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(1, TimeUnit.MINUTES) // <1>
              .withSchedule(SimpleScheduleBuilder.simpleSchedule() // <2>
                  .withIntervalInMinutes(1) // <3>
                  .repeatForever()))); // <4>
      // end::examples.scheduling.at-fixed-rate-infinitely[]
    }

    {
      // tag::examples.scheduling.at-fixed-rate-finitely[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running every minute for total 60 times")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(1, TimeUnit.MINUTES) // <1>
              .withSchedule(SimpleScheduleBuilder.simpleSchedule() // <2>
                  .withIntervalInMinutes(1) // <3>
                  .withRepeatCount(59)))); // <4>
      // end::examples.scheduling.at-fixed-rate-finitely[]
    }

    {
      // tag::examples.scheduling.at-fixed-delay-infinitely[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running forever with a delay of 1 minute between the termination of the previous and the next execution")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(1, TimeUnit.MINUTES) // <1>
              .withSchedule(FixedDelayScheduleBuilder.repeatForever(1, TimeUnit.MINUTES)))); // <2>
      // end::examples.scheduling.at-fixed-delay-infinitely[]
    }

    {
      // tag::examples.scheduling.at-fixed-delay-finitely[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running 60 times with a delay of 1 minute between the termination of the previous and the next execution")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(1, TimeUnit.MINUTES) // <1>
              .withSchedule(FixedDelayScheduleBuilder.repeatForTotalCount(60, 1, TimeUnit.MINUTES)))); // <2>
      // end::examples.scheduling.at-fixed-delay-finitely[]
    }

    {
      // tag::examples.scheduling.cron-1[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withSchedule(CronScheduleBuilder.cronSchedule("0 15 10 ? * MON-FRI")))); // <1>
      // end::examples.scheduling.cron-1[]
    }

    {
      // tag::examples.scheduling.cron-2[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // doing something
        }
      }, Jobs.newInput()
          .withName("Running every minute starting at 14:00 and ending at 14:05, every day")
          .withRunContext(ClientRunContexts.copyCurrent())
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withSchedule(CronScheduleBuilder.cronSchedule("0 0-5 14 * * ?")))); // <1>
      // end::examples.scheduling.cron-2[]
    }

    {
      // tag::examples.scheduling.executionSemaphore[]
      IExecutionSemaphore semaphore = Jobs.newExecutionSemaphore(5); // <1>

      for (int i = 0; i < 100; i++) {
        Jobs.schedule(new IRunnable() {

          @Override
          public void run() throws Exception {
            // doing something
          }
        }, Jobs.newInput()
            .withName("job-{}", i)
            .withExecutionSemaphore(semaphore)); // <2>
      }
      // end::examples.scheduling.executionSemaphore[]
    }

    {
      // tag::examples.cancel.all-jobs-of-current-session[]
      Jobs.getJobManager().cancel(Jobs.newFutureFilterBuilder()
          .andMatch(new SessionFutureFilter(ISession.CURRENT.get()))
          .toFilter(), true);
      // end::examples.cancel.all-jobs-of-current-session[]
    }
  }

  public interface JobManagerAPI {
    // tag::jobManager.api.schedule[]
    IFuture<Void> schedule(IRunnable runnable, JobInput input); // <1>

    <RESULT> IFuture<RESULT> schedule(Callable<RESULT> callable, JobInput input); // <2>
    // end::jobManager.api.schedule[]
  }

  public class LongRunningOperation implements IRunnable {

    @Override
    public void run() throws Exception {
    }
  }

  // tag::futureFilter.example[]
  public class FutureFilter implements IFilter<IFuture<?>> {

    @Override
    public boolean accept(IFuture<?> future) {
      // Accept or reject the future
      return false;
    }
  }
  // end::futureFilter.example[]

  // tag::eventFilter.example[]
  public class EventFilter implements IFilter<JobEvent> {

    @Override
    public boolean accept(JobEvent event) {
      // Accept or reject the event
      return false;
    }
  }
  // end::eventFilter.example[]
}
