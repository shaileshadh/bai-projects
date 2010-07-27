
-- Use a slightly simplified formula; just iterate the chain. To avoid
-- rounding errors use 10*n instead of decimals.
findMin n = fst . head . filter ((==10).snd) $ iterate next (1,10*n) where
  next (a,m) = let k = m `mod` 20 in (a+1,m*(50+25*k)`div`100+k)

main = interact (unlines . map (show . findMin . read) . lines)
