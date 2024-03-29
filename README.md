# RSO: Image metadata microservice

## Prerequisites

```bash
docker run -d --name pg-workclock-db -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=workclock-db -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar work-clock-api-1.0.0-SNAPSHOT.jar
```

## Docker commands
```bash
docker build -t workclock .   
docker images
docker run workclock    
docker tag workclock janezs12/workclock   
docker push janezs12/workclock
```

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f .\deployment.yaml 
kubectl apply -f .\deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
kubectl logs work-clock-deployment-84db9697f5-fk9sp
kubectl delete pod work-clock-deployment-84db9697f5-fk9sp
```

## Scale
```bash
kubectl scale deployment/work-clock-deployment-v2 --replicas=0 --namespace=default
kubectl scale deployment/work-clock-services-deployment-v2 --replicas=0 --namespace=default
```

## Rollout - redeploy
```bash
kubectl rollout restart deployment/work-clock-services-deployment-v2
```

## Change context
```bash
kubectl config get-contexts
kubectl config use-context local
```

## Swagger link
http://localhost:8002/api-specs/ui
