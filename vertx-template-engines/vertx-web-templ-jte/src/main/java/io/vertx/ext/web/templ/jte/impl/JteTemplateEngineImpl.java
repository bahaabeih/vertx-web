/*
 * Copyright 2014 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.ext.web.templ.jte.impl;

import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.templ.jte.JteTemplateEngine;

import java.util.Map;


/**
 * @author <a href="mailto:andy@mazebert.com">Andreas Hager</a>
 */
public class JteTemplateEngineImpl implements JteTemplateEngine {

  private final TemplateEngine templateEngine;

  /**
   * Creates a vert.x template engine for the given jte template engine.
   * <p>
   * Use this method if you want full control over the used engine.
   * <p>
   * For instance, it is recommended to use the jte-maven-plugin to precompile all jte templates
   * during maven build. If you do so, you can pass a precompiled engine when running in production.
   *
   * @param templateEngine the configured jte template engine
   */
  public JteTemplateEngineImpl(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Override
  public void render(Map<String, Object> context, String templateFile, Handler<AsyncResult<Buffer>> handler) {
    try {
      StringOutput output = new StringOutput();
      templateEngine.render(templateFile, context, output);
      handler.handle(Future.succeededFuture(Buffer.buffer(output.toString())));
    } catch (RuntimeException ex) {
      handler.handle(Future.failedFuture(ex));
    }
  }

  @Override
  public void clearCache() {
    // No-Op
  }

}
