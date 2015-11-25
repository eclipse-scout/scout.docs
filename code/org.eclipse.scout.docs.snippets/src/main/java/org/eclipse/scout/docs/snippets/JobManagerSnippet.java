package org.eclipse.scout.docs.snippets;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.job.DoneEvent;
import org.eclipse.scout.rt.platform.job.IBlockingCondition;
import org.eclipse.scout.rt.platform.job.IDoneHandler;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.IJobManager;
import org.eclipse.scout.rt.platform.job.JobInput;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.job.listener.IJobListener;
import org.eclipse.scout.rt.platform.job.listener.JobEvent;
import org.eclipse.scout.rt.platform.job.listener.JobEventType;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.shared.ISession;
import org.eclipse.scout.rt.shared.job.filter.event.SessionJobEventFilter;

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
      return "finished";
    }
  }

  // end::Callable[]

  void snippets() throws Exception {

    // tag::queryCancellationStatus[]
    // This is the preferred way, because this also works if not running in a job.
    if (RunMonitor.CURRENT.get().isCancelled()) {
      return; // job was cancelled, exit work
    }

    // Alternative, which is only shown for completeness.
    if (IFuture.CURRENT.get().isCancelled()) {
      return; // job was cancelled, exit work
    }
    // end::queryCancellationStatus[]

    // tag::obtainAssociatedFuture[]

    IFuture<?> currentFuture = IFuture.CURRENT.get();

    // end::obtainAssociatedFuture[]

    // tag::scheduleByJobManager[]
    IJobManager jobManager = BEANS.get(IJobManager.class); // <1>

    JobInput jobInput = BEANS.get(JobInput.class); // <2>
    jobInput.withName("job-name").withRunContext(RunContexts.copyCurrent()); // <3>

    jobManager.schedule(new Callable<String>() { // <4>

      @Override
      public String call() throws Exception {
        return "do some work";
      }
    }, jobInput);
    // end::scheduleByJobManager[]
  }

  void snippet1() throws Exception {
    // tag::scheduleByFactory[]
    Jobs.schedule(new IRunnable() { // <1>

      @Override
      public void run() throws Exception {
        // do some work
      }
    }, Jobs.newInput()
        .withRunContext(RunContexts.copyCurrent()));

    Jobs.schedule(new IRunnable() { // <2>

      @Override
      public void run() throws Exception {
        // do some work
      }
    }, Jobs.newInput()
        .withRunContext(RunContexts.copyCurrent())
        .withName("job-name")); // <3>
    // end::scheduleByFactory[]
  }

  void snippet_createEmptyRunContext() throws Exception {
    // tag::RunContexts.empty[]
    IFuture<?> currentFuture = IFuture.CURRENT.get();
    RunMonitor currentRunMonitor = RunMonitor.CURRENT.get();

    currentRunMonitor.isCancelled();
    currentFuture.isCancelled();

    // end::RunContexts.empty[]
  }

  void snippet_runPeriodicAction() throws Exception {
    // tag::Jobs.scheduleAtFixedRate[]
    // Create the Subject to run the job on behalf.
    Subject subject = new Subject(); // <1>
    subject.getPrincipals().add(new SimplePrincipal("john"));
    subject.setReadOnly();

    // Create the RunContext to run the job on behalf.
    // Schedule the periodic action to run every 60 seconds.
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        System.out.println("running every minute");
      }
    }, Jobs.newInput()
        .withRunContext(RunContexts.empty()
            .withSubject(subject)
            .withLocale(Locale.US))
        .withPeriodicExecutionAtFixedRate(60, TimeUnit.SECONDS));
    // end::Jobs.scheduleAtFixedRate[]
  }

  void snippet_delayedExecution() throws Exception {
    // tag::Jobs.scheduleDelayed[]
    Jobs.schedule(new IRunnable() {

      @Override
      public void run() throws Exception {
        System.out.println("this job runs with a delay of 5 seconds");
      }
    }, Jobs.newInput()
        .withRunContext(RunContexts.copyCurrent())
        .withSchedulingDelay(5, TimeUnit.SECONDS));
    // end::Jobs.scheduleDelayed[]
  }

  void snippet_blockingCondition() throws Exception {
    // tag::BlockingCondition[]

    // Create the blocking condition <1>
    final IBlockingCondition condition = Jobs.getJobManager().createBlockingCondition("Operation", true);

    // Schedule the job <2>
    IFuture<Boolean> future = Jobs.schedule(new LongRunningOperation(), Jobs.newInput()
        .withRunContext(RunContexts.copyCurrent()));

    // Register 'done callback' to be invoked once the job completes <3>
    future.whenDone(new IDoneHandler<Boolean>() {

      @Override
      public void onDone(DoneEvent<Boolean> event) {
        condition.setBlocking(false); // <5>
      }
    }, RunContexts.copyCurrent());

    // Wait for the job to complete <4>
    condition.waitFor(1, TimeUnit.MINUTES);

    System.out.println("Operation completed"); // <6>
    // end::BlockingCondition[]
  }

  void snippet_filter() throws Exception {
    // tag::BlockingCondition[]

    Jobs.getJobManager().addListener(Jobs.newEventFilterBuilder()
        .andMatchRunContext(ServerRunContext.class)
        .andMatchEventType(JobEventType.ABOUT_TO_RUN)
        .andMatch(new SessionJobEventFilter(ISession.CURRENT.get()))
        .toFilter(), new IJobListener() {

          @Override
          public void changed(JobEvent event) {
            System.out.println(event.getFuture());
          }
        });

    // end::BlockingCondition[]
  }

  void snippet_lifecycleEvents() throws Exception {
    // tag::BlockingCondition[]

    Jobs.newEventFilterBuilder()
        .andMatchRunContext(ServerRunContext.class)
        .andMatchEventType(JobEventType.ABOUT_TO_RUN)
        .andMatch(new SessionJobEventFilter(ISession.CURRENT.get()))
        .toFilter();
    // end::BlockingCondition[]
  }

  public class LongRunningOperation implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
      return null;
    }
  }
}
