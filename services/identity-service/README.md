# Identity Service

```powershell
docker compose -f services/identity-service/docker-compose.yml up -d
mvn -pl services/identity-service clean package
mvn -pl services/identity-service spring-boot:run
```
