SELECT products.id, products.price, SUM(H_orderedProducts.quantity)
FROM H_orderedProducts
         LEFT JOIN products
                   ON H_orderedProducts.productID = products.id
WHERE H_orderedProducts.BeginDate <= '2023-05-06' AND
    (H_orderedProducts.EndDate IS NULL OR H_orderedProducts.EndDate > '2023-01-07')
GROUP BY products.id;