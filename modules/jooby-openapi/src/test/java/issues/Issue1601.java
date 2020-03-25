package issues;

import io.jooby.openapi.OpenAPIResult;
import io.jooby.openapi.OpenAPITest;
import issues.i1601.App1601;
import issues.i1601.App1601b;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Issue1601 {

  @OpenAPITest(value = App1601.class)
  public void shouldParseOpenAPIDefinition(OpenAPIResult result) {
    assertEquals("openapi: 3.0.1\n"
        + "info:\n"
        + "  title: Title\n"
        + "  description: description\n"
        + "  termsOfService: Terms\n"
        + "  contact:\n"
        + "    name: Jooby\n"
        + "    url: https://jooby.io\n"
        + "    email: support@jooby.io\n"
        + "  license:\n"
        + "    name: Apache\n"
        + "    url: https://jooby.io/LICENSE\n"
        + "  version: \"10\"\n"
        + "servers:\n"
        + "- url: serverurl\n"
        + "  description: '...'\n"
        + "  variables:\n"
        + "    env:\n"
        + "      description: environment\n"
        + "      default: dev\n"
        + "      enum:\n"
        + "      - stage\n"
        + "      - prod\n"
        + "security:\n"
        + "- oauth:\n"
        + "  - read:write\n"
        + "tags:\n"
        + "- name: mytag\n"
        + "paths:\n"
        + "  /1601:\n"
        + "    get:\n"
        + "      operationId: get1601\n"
        + "      responses:\n"
        + "        \"200\":\n"
        + "          description: Success\n"
        + "          content:\n"
        + "            application/json:\n"
        + "              schema:\n"
        + "                type: string\n", result.toYaml());
  }

  @OpenAPITest(value = App1601b.class)
  public void shouldParseOpenAPIDefinitionController(OpenAPIResult result) {
    assertEquals("openapi: 3.0.1\n"
        + "info:\n"
        + "  title: Title\n"
        + "  description: description\n"
        + "  termsOfService: Terms\n"
        + "  contact:\n"
        + "    name: Jooby\n"
        + "    url: https://jooby.io\n"
        + "    email: support@jooby.io\n"
        + "  license:\n"
        + "    name: Apache\n"
        + "    url: https://jooby.io/LICENSE\n"
        + "  version: \"10\"\n"
        + "servers:\n"
        + "- url: serverurl\n"
        + "  description: '...'\n"
        + "  variables:\n"
        + "    env:\n"
        + "      description: environment\n"
        + "      default: dev\n"
        + "      enum:\n"
        + "      - stage\n"
        + "      - prod\n"
        + "security:\n"
        + "- oauth:\n"
        + "  - read:write\n"
        + "tags:\n"
        + "- name: mytag\n"
        + "paths:\n"
        + "  /1601:\n"
        + "    get:\n"
        + "      operationId: doSomething\n"
        + "      responses:\n"
        + "        \"200\":\n"
        + "          description: Success\n"
        + "          content:\n"
        + "            application/json:\n"
        + "              schema:\n"
        + "                type: string\n", result.toYaml());
  }
}
