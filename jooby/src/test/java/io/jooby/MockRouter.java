package io.jooby;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MockRouter {

  private static final Consumer NOOP = value -> {
  };

  private Supplier<Jooby> supplier;

  public MockRouter(Supplier<Jooby> supplier) {
    this.supplier = () -> {
      Jooby jooby = supplier.get();
      if (jooby.getEnvironment() == null) {
        jooby.setEnvironment(Env.create().build(jooby.getClass().getClassLoader(), "test"));
      }
      return jooby;
    };
  }

  public MockRouter(Consumer<Jooby> consumer) {
    this.supplier = () -> {
      Jooby jooby = new Jooby();
      Env env = Env.create().build(getClass().getClassLoader(), "test");
      jooby.setEnvironment(env);
      consumer.accept(jooby);
      return jooby;
    };
  }

  /* **********************************************************************************************
   * GET
   * **********************************************************************************************
   */
  public Object get(@Nonnull String path) {
    return get(path, NOOP, NOOP);
  }

  public Object get(@Nonnull String path, @Nonnull Consumer<Result> consumer) {
    return get(path, NOOP, consumer);
  }

  public Object get(@Nonnull String path, @Nonnull Consumer<MockContext> prepare,
      @Nonnull Consumer<Result> consumer) {
    return route(Router.GET, path, prepare, consumer);
  }

  /* **********************************************************************************************
   * POST
   * **********************************************************************************************
   */
  public Object post(@Nonnull String path) {
    return post(path, NOOP, NOOP);
  }

  public Object post(@Nonnull String path, @Nonnull Consumer<Result> consumer) {
    return post(path, NOOP, consumer);
  }

  public Object post(@Nonnull String path, @Nonnull Consumer<MockContext> prepare,
      @Nonnull Consumer<Result> consumer) {
    return route(Router.POST, path, prepare, consumer);
  }

  /* **********************************************************************************************
   * DELETE
   * **********************************************************************************************
   */

  public Object delete(@Nonnull String path) {
    return delete(path, NOOP, NOOP);
  }

  public Object delete(@Nonnull String path, @Nonnull Consumer<Result> consumer) {
    return delete(path, NOOP, consumer);
  }

  public Object delete(@Nonnull String path, @Nonnull Consumer<MockContext> prepare,
      @Nonnull Consumer<Result> consumer) {
    return route(Router.DELETE, path, prepare, consumer);
  }

  /* **********************************************************************************************
   * Route:
   * **********************************************************************************************
   */
  public Object route(@Nonnull String method, @Nonnull String path,
      @Nonnull Consumer<Result> consumer) {
    return route(method, path, NOOP, consumer);
  }

  public Object route(@Nonnull String method, @Nonnull String path,
      @Nonnull Consumer<MockContext> prepare,
      @Nonnull Consumer<Result> consumer) {
    return route(supplier.get(), method, path, prepare, consumer);
  }

  private Object route(Jooby router, String method, String path, Consumer<MockContext> prepare,
      Consumer<Result> consumer) {
    MockContext ctx = new MockContext()
        .setMethod(method)
        .setPathString(path);
    if (prepare != null) {
      prepare.accept(ctx);
    }
    Router.Match match = router.match(ctx);
    ctx.setPathMap(match.pathMap());
    ctx.setRoute(match.route());
    Object value = match.route().getHandler().execute(ctx);
    Result result = new Result(value, ctx.getStatusCode());

    /** Content-Type: */
    result.header("Content-Type",
        ctx.getResponseContentType().toContentTypeHeader(ctx.getResponseCharset()));

    /** Length: */
    long responseLength = ctx.getResponseLength();
    if (responseLength > 0) {
      result.header("Content-Length", Long.toString(responseLength));
    } else {
      result.header("Content-Length", Long.toString(value.toString().length()));
    }
    consumer.accept(result);
    return value;
  }

  public MockRouter apply(Consumer<MockRouter> consumer) {
    consumer.accept(this);
    return this;
  }
}