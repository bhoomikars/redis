# Application name is redis-swagger

Config maps wiht key-value https://github.com/bhoomikars/redis/commit/7fd989f5800d8488a7981065b269207772dcb8a9

1. Define a Dockerfile like below
 - FROM openjdk:8-jre
 - ADD target/redis-swagger-0.0.8-SNAPSHOT.jar app.jar
 - EXPOSE 8080
 - ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar","--spring.config.location=/config/application.prod.properties"]

 when you do mvn clean install .jar is created under /target take that jar and just rename it to app.jar
 - EXPOSE 8080 -> it is the container port which we are exposing(container port should match to your app port)
 - ENTRYPOINT -> simplified : java app.jar(.jar under target which we have renamed) --spring.config.location=loaction.your.properties
 - NOTE: this is not mvn command 


2. Define config-map file in deployment.yml for k8

<img width="470" alt="Screenshot 2023-02-23 at 2 29 10 PM" src="https://user-images.githubusercontent.com/57263117/220862084-83471034-91b7-4d19-bee9-63b598f244d7.png">

- here key is config.app.properties use pipe(|) and add all your properties 
- apiVersion: v1
data:
  config.app.properties: |
    spring.redis.database=0
    spring.redis.host=redis-svc
    spring.redis.port=6379
    test.val=prodTestFile
- kind: ConfigMap

  
 - Test:  kubectl get configmaps
   - kubekubectl describe configmaps config-maps 
        
  <img width="1073" alt="Screenshot 2023-02-23 at 1 50 56 PM" src="https://user-images.githubusercontent.com/57263117/220854829-b449c83f-d858-48ea-9655-77084bd96cb0.png">


3. Define Service
<img width="575" alt="Screenshot 2023-02-23 at 2 31 08 PM" src="https://user-images.githubusercontent.com/57263117/220862540-ff5a9656-32f6-400d-966b-965e80afdd17.png">


      
   - Here Service Port : 8085
   - target port is your conatiner port which is EXPOSE in your docker file
   - Nodeport is external client port which is 30061
   <img width="790" alt="Screenshot 2023-02-23 at 1 54 02 PM" src="https://user-images.githubusercontent.com/57263117/220855527-110efaf6-37f9-43d2-9378-b97cf38a32a7.png">

   
4. Refer config-maps under volumes use the key(config.app.properties)
  <img width="676" alt="Screenshot 2023-02-23 at 2 33 19 PM" src="https://user-images.githubusercontent.com/57263117/220863219-11027469-d40b-44fc-9896-864adf389c04.png">

5. use config-volume in your container 
<img width="913" alt="Screenshot 2023-02-23 at 2 36 06 PM" src="https://user-images.githubusercontent.com/57263117/220863984-a30b1133-9b83-4495-a5b8-96dd70966c54.png">


6 use same config.location in your docker --spring.config.location=/config/application.prod.properties

  Connecting to prod server:
  
   kubectl port-forward redis-55b654cbc9-4dgfr 6379:6379 
