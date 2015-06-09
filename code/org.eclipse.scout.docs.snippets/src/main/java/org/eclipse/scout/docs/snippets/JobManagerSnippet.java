package org.eclipse.scout.docs.snippets;

import java.util.concurrent.Callable;

import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunContexts;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.IJobManager;
import org.eclipse.scout.rt.platform.job.JobInput;
import org.eclipse.scout.rt.platform.job.Jobs;

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
    jobInput.name("job-name").runContext(RunContexts.copyCurrent()); // <3>

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
    });

    Jobs.schedule(new IRunnable() { // <2>

      @Override
      public void run() throws Exception {
        // do some work
      }
    }, Jobs.newInput(RunContexts.copyCurrent()).name("job-name")); // <3>
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
}
