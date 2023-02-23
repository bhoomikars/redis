# Application name is redis-swagger

1. Define a Dockerfile like below

 FROM openjdk:8-jre
 ADD target/redis-swagger-0.0.8-SNAPSHOT.jar app.jar
 EXPOSE 8080
 ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--spring.config.location=/config/application.prod.properties"]

 when you do mvn clean install .jar is created under /target take that jar and just rename it to app.jar
 EXPOSE 8080 -> it is the container port which we are exposing(container port should match to your app port)
 ENTRYPOINT -> simplified : java app.jar(.jar under target which we have renamed) --spring.config.location=loaction.your.properties
 NOTE: this is not mvn command 


2. Define config-map file in deployment.yml for k8

apiVersion: v1
data:
  config.app.properties: |
    spring.redis.database=0
    spring.redis.host=redis-svc
    spring.redis.port=6379
    test.val=prodTestFile
kind: ConfigMap
metadata:
  name: config-maps
  namespace: default

here key is config.app.properties use pipe(|) and add all your properties 
apiVersion: v1
data:
  config.app.properties: |
    spring.redis.database=0
    spring.redis.host=redis-svc
    spring.redis.port=6379
    test.val=prodTestFile
kind: ConfigMap
metadata:
  name: config-maps
  namespace: default
  
 Test:  kubectl get configmaps
        kubekubectl describe configmaps config-maps 
        
  <img width="1073" alt="Screenshot 2023-02-23 at 1 50 56 PM" src="https://user-images.githubusercontent.com/57263117/220854829-b449c83f-d858-48ea-9655-77084bd96cb0.png">


3. Define Service

---
apiVersion: v1
kind: Service
metadata:
  name: redis-swagger
  labels:
    app: redis-swagger
spec:
  type: NodePort
  selector:
    app: redis-swagger
  ports:
    - protocol: TCP
      port: 8085
      name: http
      targetPort: 8080
      nodePort: 30061
      
   Here Service Port : 8085
   target port is your conatiner port which is EXPOSE in your docker file
   Nodeport is external client port which is 30061
   <img width="790" alt="Screenshot 2023-02-23 at 1 54 02 PM" src="https://user-images.githubusercontent.com/57263117/220855527-110efaf6-37f9-43d2-9378-b97cf38a32a7.png">

   
   
  
  
