import Char
import List
run = do
	f <- readFile "p40.in"
	return . length . nub . words . map toLower .
		(filter (\c -> isAlpha c || isSpace c)) . concat $ lines f
