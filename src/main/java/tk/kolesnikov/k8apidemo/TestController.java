package tk.kolesnikov.k8apidemo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@RestController
public class TestController {

 @GetMapping("/test")
  List<String> test() throws IOException, ApiException {
  	List<String> pods = new ArrayList<>();

  	// loading the in-cluster config, including:
    //   1. service-account CA
    //   2. service-account bearer-token
    //   3. service-account namespace
    //   4. master endpoints(ip, port) from pre-set environment variables
    ApiClient client = ClientBuilder.cluster().build();

    // if you prefer not to refresh service account token, please use:
    // ApiClient client = ClientBuilder.oldCluster().build();

    // set the global default api-client to the in-cluster one from above
    Configuration.setDefaultApiClient(client);

    // the CoreV1Api loads default api-client from global configuration.
    CoreV1Api api = new CoreV1Api();

    // invokes the CoreV1Api client
    V1PodList list =
        api.listNamespacedPod("default", null, null, null, null, null, null, null, null, null, null);
    for (V1Pod item : list.getItems()) {
      pods.add(item.getMetadata().getName());
    }

    return pods;
  }

}
