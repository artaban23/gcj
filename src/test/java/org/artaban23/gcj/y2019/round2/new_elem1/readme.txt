X - amount of codiums atoms
Y - amount of Jamarium atoms
Wi - weight of a molecule i
Wi = (k*Xi, m*Yi)
k amd m - atom weights

for any i and j (Xi < Xj)
    when (Yi > Yj)
    when Wi < Wj
        k*Xi + m*Yi < k*Xj + m*Yj
        m(Yi - Yj) < k(Xj - Xi)
        m/k < (Xj - Xi)/(Yi - Yj)

for any i and j (Xi > Xj)
    when (Yi < Yj)
    when Wi < Wj
        k*Xi + m*Yi < k*Xj + m*Yj
        k(Xi - Xj) < m(Yj - Yi)
        m/k > (Xi - Xj)/(Yj - Yi) -> m/k > (Xj - Xi)/(Yi - Yj)
        m/k > (Xj - Xi)/(Yi - Yj)

for any Wi Wj and Wq
if(Wi < Wj)
Wq should honour previous boundaries or ELSE discard this order


---
(Xj - Xi)/(Yi - Yj)
(Xq - Xo)/(Yo - Yq)
