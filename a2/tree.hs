tempCostMTArray :: [[Int]] -> Int
tempCostMTArray [] = 0
tempCostMTArray [x] = tempCostMTPair (x !! 0) (x !! 1)
tempCostMTArray (x:xs) = (tempCostMTPair (x !! 0) (x !! 1)) + (tempCostMTArray xs)

sortMTArray :: [[Int]] -> [[Int]]
sortMTArray [] = []
sortMTArray (x:xs) = 
            let smallerSorted = sortMTArray [a | a <- xs, (tempCostMTPair (a !! 0) (a !! 1)) <= (tempCostMTPair (x !! 0) (x !! 1))]
                biggerSorted = sortMTArray [a | a <- xs, (tempCostMTPair (a !! 0) (a !! 1)) > (tempCostMTPair (x !! 0) (x !! 1))]  
            in  smallerSorted ++ [x] ++ biggerSorted

tempCostMTPair :: Int -> Int -> Int
tempCostMTPair x y = (costs !! x) !! y
                where costs = [[1..8],[1..8],[1..8],[1..8],[1..8],[1..8],[1..8],[1..8]]


