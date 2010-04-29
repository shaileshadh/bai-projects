import System.Environment

hop :: Int -> [String]
hop n = map f [1..n] where
	f m
		| m `mod` 15 == 0 = "Hop"
		| m `mod` 5 == 0 = "Hophop"
		| m `mod` 3 == 0 = "Hoppity"
		| otherwise = ""

trim = trim' . trim' where
	trim' s = dropWhile (==' ') (reverse s)

main = mapM_ ePut . hop . read . head . lines
	=<< readFile . head =<< getArgs where
		ePut s = case s of []->return (); s->putStrLn s
