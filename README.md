# balatro4j

## A Balatro Seed Generator coded in Java

```java
    static void generate() {
        for (int i = 0; i < 100_000; i++) {
            var seed = generateRandomString();
            var result = new Balatro()
                    .performAnalysis(seed);

            if (result.hasLegendary(1, LegendaryJoker.Perke)  && result.hasInShop(1, RareJoker.Blueprint)) {
                System.err.println(seed);
            }
        }
    }
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

Seeds found in 1 minute