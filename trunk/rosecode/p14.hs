cyc x = takeWhile (>1) $ iterate (\n -> if even n then n `div` 2 else 3*n+1) x
cyclen = succ . length . cyc
run = maximum $ map cyclen [1..10^6]
main = print run
