## Lab 3

### Notes
Lógica Bottom Up:
- A - Testes do Repository - Não precisa carregar maior parte do Application Context
- B - Envolve mock da camada do repository
- C - Para testar o controller seria melhor não ter de carregar os services sendo usado o MockBean
- D,E - Testes de Integração carregando tudo o que é normal de uma aplicação spring boot

- Só vai estar disponível para código de teste se tiver este scope: 
```xml
<scope>test</scope>
```

 


---
### Exercises
