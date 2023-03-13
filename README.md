# K8 API Demo

### Create Cluster
```
kind create cluster --name cluster-1 --wait 5m --config kind-config.yaml
```

### Build
```
mvn clean package
docker build -t sekolq/k8apidemo:0.3 .
docker push sekolq/k8apidemo:0.3
```

### Deploy
```
kubectl create -f rolebinding.yaml -n default
kubectl create -f deployment.yaml -n default
```

### Test
```
# After Pod Port Forwarding done
http://localhost:8080/test
```
