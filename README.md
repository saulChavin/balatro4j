# balatro4j

A balatro seed analyzer and finder, coded in pure Java based in Immolate

```java
var run = Balatro.builder("2K9H9HN")
        .maxAnte(1)
        .build()
        .analyze();

System.out.

println(run.toJson());
```

output:

```json
{
  "seed": "2K9H9HN",
  "antes": [
    {
      "ante": 1,
      "shopQueue": [
        {
          "item": "Drunkard"
        },
        {
          "item": "Half Joker"
        },
        {
          "item": "Eri"
        },
        {
          "item": "Burglar"
        },
        {
          "item": "Blackboard"
        },
        {
          "item": "The Emperor"
        },
        {
          "item": "Drunkard"
        },
        {
          "item": "Splash"
        },
        {
          "item": "Justice"
        },
        {
          "item": "To the Moon"
        },
        {
          "item": "The Devil"
        },
        {
          "item": "Eri"
        },
        {
          "item": "Even Steven"
        },
        {
          "item": "Gift Card"
        },
        {
          "item": "Mercury"
        }
      ],
      "tags": [
        "D6_Tag",
        "Boss_Tag"
      ],
      "voucher": "Directors_Cut",
      "boss": "The_Club",
      "packs": [
        {
          "type": "Buffoon_Pack",
          "size": 2,
          "choices": 1,
          "options": [
            {
              "name": "Raised Fist"
            },
            {
              "name": "Baseball Card"
            }
          ],
          "kind": "Buffoon"
        },
        {
          "type": "Arcana_Pack",
          "size": 3,
          "choices": 1,
          "options": [
            {
              "name": "The Lovers"
            },
            {
              "name": "The Moon"
            },
            {
              "name": "The World"
            }
          ],
          "kind": "Arcana"
        },
        {
          "type": "Buffoon_Pack",
          "size": 2,
          "choices": 1,
          "options": [
            {
              "name": "Jolly Joker"
            },
            {
              "name": "Gift Card"
            }
          ],
          "kind": "Buffoon"
        },
        {
          "type": "Standard_Pack",
          "size": 3,
          "choices": 1,
          "options": [
            {
              "name": "Gold 3 of Clubs"
            },
            {
              "name": "Bonus 5 of Hearts"
            },
            {
              "name": "Gold Queen of Spades"
            }
          ],
          "kind": "Standard"
        }
      ]
    }
  ],
  "firstAnte": {
    "ante": 1,
    "shopQueue": [
      {
        "item": "Drunkard"
      },
      {
        "item": "Half Joker"
      },
      {
        "item": "Eri"
      },
      {
        "item": "Burglar"
      },
      {
        "item": "Blackboard"
      },
      {
        "item": "The Emperor"
      },
      {
        "item": "Drunkard"
      },
      {
        "item": "Splash"
      },
      {
        "item": "Justice"
      },
      {
        "item": "To the Moon"
      },
      {
        "item": "The Devil"
      },
      {
        "item": "Eri"
      },
      {
        "item": "Even Steven"
      },
      {
        "item": "Gift Card"
      },
      {
        "item": "Mercury"
      }
    ],
    "tags": [
      "D6_Tag",
      "Boss_Tag"
    ],
    "voucher": "Directors_Cut",
    "boss": "The_Club",
    "packs": [
      {
        "type": "Buffoon_Pack",
        "size": 2,
        "choices": 1,
        "options": [
          {
            "name": "Raised Fist"
          },
          {
            "name": "Baseball Card"
          }
        ],
        "kind": "Buffoon"
      },
      {
        "type": "Arcana_Pack",
        "size": 3,
        "choices": 1,
        "options": [
          {
            "name": "The Lovers"
          },
          {
            "name": "The Moon"
          },
          {
            "name": "The World"
          }
        ],
        "kind": "Arcana"
      },
      {
        "type": "Buffoon_Pack",
        "size": 2,
        "choices": 1,
        "options": [
          {
            "name": "Jolly Joker"
          },
          {
            "name": "Gift Card"
          }
        ],
        "kind": "Buffoon"
      },
      {
        "type": "Standard_Pack",
        "size": 3,
        "choices": 1,
        "options": [
          {
            "name": "Gold 3 of Clubs"
          },
          {
            "name": "Bonus 5 of Hearts"
          },
          {
            "name": "Gold Queen of Spades"
          }
        ],
        "kind": "Standard"
      }
    ]
  }
}
```

```java
var seeds = Balatro.finder(1, 1_000_000)
        .configuration(config -> config.maxAnte(1))
        .filter(LegendaryJoker.Perkeo.inPack(1)
                .or(LegendaryJoker.Triboulet.inPack(1))
                .and(RareJoker.Blueprint.inShop(1)))
        .find();
```

## Example seeds found with Perkeo And Blueprint at Ante 1

```
CL7GH6H
X7CXUJP
WNL26QF
3K2YXQU
S258PHZ
L3F8RIJ
TOB4W98
AJSOD4X
N2DLGEJ
P36G9DH
E74BJ11
NG5OCZP
H7ENEO3
U5KN877
ZG4PCNG
9D7T4NL
MG7LPGN
JVT8XAK
JRMGDQ1
LPQW89R
JACU3YR
U7JZ2IP
Q6NVSEK
PDOFM57
R2GYU2O
4O5N1WE
LOO1XGD
CY8GJHV
D56BSVH
T41RPVX
K6ORMCO
OHME2GT
XE13DOC
JQ7UY5Q
VVBS744
BNSJKVT
SAVPR55
GNAL7N5
27LFAKN
M3NHKVF
A9WBD1R
8CWQ9HF
3YZF6BN
XKLMQWV
N9E9D32
IXMJ3LP
2A55NLM
9WPH7DG
WHOQQZD
3WBK1NK
```

average speed: 200K seeds per second