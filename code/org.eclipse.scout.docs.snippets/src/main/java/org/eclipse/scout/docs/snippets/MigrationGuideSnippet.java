package org.eclipse.scout.docs.snippets;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.context.RunMonitor;
import org.eclipse.scout.rt.platform.job.FixedDelayScheduleBuilder;
import org.eclipse.scout.rt.platform.job.IFuture;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.server.clientnotification.ClientNotificationRegistry;
import org.eclipse.scout.rt.server.context.ServerRunContexts;
import org.eclipse.scout.rt.shared.notification.INotificationHandler;

public class MigrationGuideSnippet {

  void snippetsJobManager() throws Exception {
    {
      /*
      // tag::jobManager.rawEclipseJob.old[]
      new Job("job-name") {
      
        @Override
        protected IStatus run(IProgressMonitor monitor) {
          // do something
        }
      }.schedule();
      // end::jobManager.rawEclipseJob.old[]
       */
    }

    {
      // tag::jobManager.rawEclipseJob.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name"));
      // end::jobManager.rawEclipseJob.new[]
    }

    {
      /*
      // tag::jobManager.clientSyncJob.old[]
      new ClientSyncJob("job-name", ClientSessionProvider.currentSession()) {
      
        @Override
        protected void runVoid(IProgressMonitor monitor) throws Throwable {
          // do something
        }
      }.schedule();
      // end::jobManager.clientSyncJob.old[]
      */
    }

    {
      // tag::jobManager.clientSyncJob.new[]
      ModelJobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, ModelJobs
          .newInput(ClientRunContexts.copyCurrent())
          .withName("job-name"));
      // end::jobManager.clientSyncJob.new[]
    }

    {
      /*
      // tag::jobManager.clientAsyncJob.old[]
      new ClientAsyncJob("job-name", ClientSessionProvider.currentSession()) {
      
        @Override
        protected void runVoid(IProgressMonitor monitor) throws Throwable {
          // do something
        }
      }.schedule();
      // end::jobManager.clientAsyncJob.old[]
       */
    }

    {
      // tag::jobManager.clientAsyncJob.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withRunContext(ClientRunContexts.copyCurrent())
          .withName("job-name"));
      // end::jobManager.clientAsyncJob.new[]
    }

    {
      /*
      // tag::jobManager.serverJob.old[]
      new ServerJob("job-name", ServerJob.getCurrentSession()) {
      
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
      }.schedule();
      // end::jobManager.serverJob.old[]
       */
    }

    {
      // tag::jobManager.serverJob.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withRunContext(ServerRunContexts.copyCurrent())
          .withName("job-name"));
      // end::jobManager.serverJob.new[]
    }

    {
      /*
      // tag::jobManager.serverJob.runNow.old[]
      new ServerJob("job-name", ServerJob.getCurrentSession()) {
      
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
      }.runNow(new NullProgressMonitor());
      // end::jobManager.serverJob.runNow.old[]
       */
    }

    {
      // tag::jobManager.serverJob.runNow.new[]
      ServerRunContexts.copyCurrent().run(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      });
      // end::jobManager.serverJob.runNow.new[]
    }

    {
      /*
      // tag::jobManager.delayedExecution.old[]
      new Job("job-name") {
      
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
      }
      }.schedule(5_000);
      // end::jobManager.delayedExecution.old[]
       */
    }

    {
      // tag::jobManager.delayedExecution.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name")
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withStartIn(5, TimeUnit.SECONDS)));
      // end::jobManager.delayedExecution.new[]
    }

    {
      /*
      // tag::jobManager.fixedDelayExecution.old[]
      new Job("job-name") {
      
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do something
        schedule(5_000);
      }
      }.schedule(5_000);
      // end::jobManager.fixedDelayExecution.old[]
       */
    }

    {
      // tag::jobManager.fixedDelayExecution.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name")
          .withExecutionTrigger(Jobs.newExecutionTrigger()
              .withSchedule(FixedDelayScheduleBuilder.repeatForever(5, TimeUnit.SECONDS))
              .withStartIn(5, TimeUnit.SECONDS)));
      // end::jobManager.fixedDelayExecution.new[]
    }

    {
      /*
      // tag::jobManager.serverJob.otherSubject.old[]
      new ServerJob("job-name", ServerJob.getCurrentSession(), subject) {
      
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) throws Exception {
        // do something
        return Status.OK_STATUS;
      }
      }.schedule();
      // end::jobManager.serverJob.otherSubject.old[]
       */
    }

    {
      Subject subject = null;
      // tag::jobManager.serverJob.otherSubject.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name")
          .withRunContext(ServerRunContexts.copyCurrent()
              .withSubject(subject)));
      // end::jobManager.serverJob.otherSubject.new[]
    }

    {
      /*
      // tag::jobManager.job.checkForCancellation.old[]
      new Job("job-name") {
      
      @Override
      protected IStatus run(IProgressMonitor monitor) {
        // do first chunk of work
        if (monitor.isCanceled()) {
            return Status.CANCEL_STATUS;
        }
        // do second chunk of work
        if (monitor.isCanceled()) {
          return Status.CANCEL_STATUS;
        }
        // do third chunk of work
        return Status.OK_STATUS;
      }
      }.schedule();
      // end::jobManager.job.checkForCancellation.old[]
       */
    }

    {
      // tag::jobManager.job.checkForCancellation.new[]
      Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do first chunk of work
          if (RunMonitor.CURRENT.get().isCancelled()) {
            return;
          }
          // do second chunk of work
          if (RunMonitor.CURRENT.get().isCancelled()) {
            return;
          }
          // do third chunk of work
        }
      }, Jobs.newInput()
          .withName("job-name"));
      // end::jobManager.job.checkForCancellation.new[]
    }

    {
      /*
      // tag::jobManager.job.join.old[]
      Job job = new Job("job-name") {
      
        @Override
        protected IStatus run(IProgressMonitor monitor) {
          // do something
          return Status.OK_STATUS;
        }
      };
      job.schedule();
      job.join();
      // end::jobManager.job.join.old[]
       */
    }

    {
      // tag::jobManager.job.join.new[]
      IFuture<Void> future = Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name"));

      future.awaitDone();
      // end::jobManager.job.join.new[]
    }

    {
      /*
      // tag::jobManager.job.join_with_timeout.old[]
      Job job = new Job("job-name") {
      
        @Override
        protected IStatus run(IProgressMonitor monitor) {
          // do something
          return Status.OK_STATUS;
        }
      };
      job.schedule();
      job.join(5_000, new NullProgressMonitor());
      // end::jobManager.job.join_with_timeout.old[]
       */
    }

    {
      // tag::jobManager.job.join_with_timeout.new[]
      IFuture<Void> future = Jobs.schedule(new IRunnable() {

        @Override
        public void run() throws Exception {
          // do something
        }
      }, Jobs.newInput()
          .withName("job-name"));

      future.awaitDone(5, TimeUnit.SECONDS);
      // end::jobManager.job.join_with_timeout.new[]
    }

    {
      /*
      // tag::jobManager.job.get_result.old[]
      final AtomicReference<String> result = new AtomicReference<>();
      
      Job job = new Job("job-name") {
      
        @Override
        protected IStatus run(IProgressMonitor monitor) {
          // do something
          result.set("abc");
          return Status.OK_STATUS;
        }
      };
      job.schedule();
      job.join();
      System.out.println(result);
      // end::jobManager.job.get_result.old[]
       */
    }

    {
      // tag::jobManager.job.get_result.new[]
      IFuture<String> future = Jobs.schedule(new Callable<String>() {

        @Override
        public String call() throws Exception {
          // do something
          return "result";
        }
      }, Jobs.newInput()
          .withName("job-name"));

      String result = future.awaitDoneAndGet();
      System.out.println(result);
      // end::jobManager.job.get_result.new[]
    }
  }

  void snippetsClientNotificationPublish() {
    {
      /*
      // tag::clientnotification.publish.old[]
      SERVICES.getService(IClientNotificationService.class)
          .putNotification(new UserChangedClientNotification(userId), new UserKeyClientNotificationFilter(userId, 60000L));
      // end::clientnotification.publish.old[]
       */
    }

    {
      // tag::clientnotification.publish.new[]
      String userId = "testUser";
      BEANS.get(ClientNotificationRegistry.class)
          .putForUser(userId, new UserChangedClientNotification(userId));
      // end::clientnotification.publish.new[]
    }

  }

  void snippetsClientNotificationHandle() {
    {
      /*
      // tag::clientnotification.handle.old[]
      IClientNotificationConsumerListener m_userChangedNotificationListener = new IClientNotificationConsumerListener() {
      @Override
      public void handleEvent(ClientNotificationConsumerEvent event, boolean sync) {
        if (event.getClientNotification() instanceof UserChangedClientNotification) {
          //handle ...
        }
      }
      };
      SERVICES.getService(IClientNotificationConsumerService.class)
          .addClientNotificationConsumerListener(AbstractCoreClientSession.get(), m_userChangedNotificationListener);
      // end::clientnotification.handle.old[]
      */
    }
  }

  // tag::clientnotification.handle.new[]
  class UserChangedClientNotificationHandler implements INotificationHandler<UserChangedClientNotification> {

    @Override
    public void handleNotification(UserChangedClientNotification notification) {
      //handle ...
    }
  }
  // end::clientnotification.handle.new[]

  class UserChangedClientNotification implements Serializable {
    private static final long serialVersionUID = 1L;
    @SuppressWarnings("unused")
    private String m_userId;

    public UserChangedClientNotification(String userId) {
      m_userId = userId;
    }
  }
}
