parse f = let a = lines f in (read (a!!0), read (a!!1)) :: (Int,Int)
run (a,b) = let s = ceiling ((realToFrac a)**(1/6)) in length . takeWhile (<=b) $ map (^6) [s..]
main = interact (show . run . parse)
