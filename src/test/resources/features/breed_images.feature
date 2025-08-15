@regressao
Feature: Imagens por raça

  @fumaca @basico
  Scenario: Listar imagens de uma raça específica
    Given a raça "pug"
    When eu envio GET para "/breed/{breed}/images"
    Then o status code deve ser 200
    And a lista de imagens não deve estar vazia
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a raça atual

  @regressao @multiplas
  Scenario Outline: Múltiplas imagens por raça com counts válidos
    Given a raça "<breed>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/images/random/{count}"
    Then o status code deve ser 200
    And a quantidade de imagens deve ser exatamente <count>
    And todas as URLs devem ser válidas
    And todas as URLs devem conter a raça atual
    Examples:
      | breed     | count |
      | hound     | 1     |
      | pug       | 3     |
      | retriever | 25    |

  @limite
  Scenario Outline: Solicitação não deve exceder o count solicitado
    Given a raça "<breed>"
    And o count <count>
    When eu envio GET para "/breed/{breed}/images/random/{count}"
    Then o status code deve ser 200
    And deve retornar no máximo <count> imagens
    And todas as URLs devem ser válidas
    Examples:
      | breed         | count |
      | affenpinscher | 50    |
      | bluetick      | 50    |
      | clumber       | 50    |
      | eskimo        | 50    |
      | otterhound    | 50    |
      | boxer         | 50    |
      | akita         | 10    |

  @negativo
  Scenario: Raça inválida deve retornar erro
    Given a raça "dragon"
    When eu envio GET para "/breed/{breed}/images"
    Then a resposta não deve ser 200
