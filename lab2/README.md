## Lab 2

### Notes
- mock: objeto com interface compatível
- **REASONS TO USE MOCK**:
  - not yet available
  - variability
  - slow latency (torna lento o teste unitário que é na ordem dos milissegundos)
  - $
- ```mvn test``` -> corre apenas testes unitários
- ```mvn install failsafe:integration-test``` -> corre testes unitários + testes de integração
---
### Exercises
#### 2.2.b) 
The SuT is AddressResolverService. The service to mock is the interface ISimpleHttpClient which It is not yet implemented.
#### 2.3)
Sem internet, os testes de integração vão falhar visto não conseguir chamar a API. 
