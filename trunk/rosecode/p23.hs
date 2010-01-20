divs = foldl1 lcm [1..15]
hex n = n*(2*n-1)
run = length . filter (\x -> x `mod` divs == 0) $ map hex [1..10^8]
