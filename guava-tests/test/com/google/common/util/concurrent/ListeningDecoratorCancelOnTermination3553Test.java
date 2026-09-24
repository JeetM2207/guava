package com.google.common.util.concurrent;

import static com.google.common.util.concurrent.MoreExecutors.listeningDecorator;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.Test;

// https://github.com/google/guava/issues/3553: a ListenableFuture returned by
// MoreExecutors#listeningDecorator's scheduleAtFixedRate never completes once the
// underlying ScheduledExecutorService is shut down -- unlike the same future wrapped via
// JdkFutureAdapters#listenInPoolThread, which correctly transitions to cancelled. The
// decorator's NeverSuccessfulListenableFutureTask apparently never fails/cancels either.
public class ListeningDecoratorCancelOnTermination3553Test {
  private static final Runnable DO_NOTHING = () -> {};

  @Test
  public void testScheduledExecutorServiceWithListeningDecoratorCancelsOnTermination()
      throws InterruptedException, TimeoutException, ExecutionException {
    ListeningScheduledExecutorService service = listeningDecorator(newScheduledThreadPool(1));
    ListenableFuture<?> future = service.scheduleAtFixedRate(DO_NOTHING, 0L, 1, SECONDS);

    assertFalse(future.isDone());

    service.shutdown();
    service.awaitTermination(5, SECONDS);
    service.shutdownNow();

    try {
      future.get(10, SECONDS);
      fail("Expected to throw an exception.");
    } catch (CancellationException ignored) {
      // expected
    }

    assertTrue(future.isCancelled());
  }
}
