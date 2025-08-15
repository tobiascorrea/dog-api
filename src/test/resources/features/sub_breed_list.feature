@regressao @subracas
Feature: Lista de sub-raças

  # Raças que devem possuir sub-raças
  @regressao @subracas
  Scenario Outline: Listar sub-raças para raças com sub-raças
    Given a raça "<breed>"
    When eu envio GET para "/breed/{breed}/list"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a lista de sub-raças deve conter itens
    Examples:
      | breed     |
      | hound     |
      | bulldog   |
      | terrier   |
      | retriever |
      | spaniel   |
      | mastiff   |

  # Raças sem sub-raças
  @basico @subracas
  Scenario Outline: Listar sub-raças para raças sem sub-raças
    Given a raça "<breed>"
    When eu envio GET para "/breed/{breed}/list"
    Then o status code deve ser 200
    And o campo status deve ser "success"
    And a lista de sub-raças deve estar vazia
    Examples:
      | breed    |
      | pug      |
      | beagle   |
      | labrador |

  @negativo @subracas
  Scenario: Raça inválida para listagem de sub-raças
    Given a raça "dragondog"
    When eu envio GET para "/breed/{breed}/list"
    Then a resposta não deve ser 200
