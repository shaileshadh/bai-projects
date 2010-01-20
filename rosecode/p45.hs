--A028859
a :: [Integer]
a = 1:3: zipWith (\a b -> 2*a+2*b) a (tail a)

run = a!!150
