
-- Integer with trailing zeroes removed
removeZeroes x = head . filter (\n -> n `mod` 10 > 0) $
  iterate (\n -> n `div` 10) x

-- Calculuate the absurdity of a price
absurdity x = let
  r = removeZeroes x
  d = fromIntegral . length $ show r
  l = r `mod` 10
  in if l == 5 then 2*d-1 else 2*d

-- The minimum possible absurdity in a given range
minabsd a b | a == b = absurdity a
minabsd a b = let
  c = show a; d = show b
  sm = fromIntegral . length . takeWhile (uncurry (==)) $ zip c d
  lst = (c!!sm,d!!sm)
  offset = if fst lst < '5' && snd lst >= '5' then 1 else 0
  in minimum [absurdity a, (2 * (sm + 1) - offset), absurdity b]

absurd x = let
  r1 = ceiling $ 0.95*(realToFrac x)
  r2 = floor $ 1.05*(realToFrac x)
  absx = absurdity x
  in absx > minabsd r1 r2

main = interact run where
  as s = let k = read s in if absurd k then "absurd" else "not absurd"
  run = unlines . map as . tail . lines

