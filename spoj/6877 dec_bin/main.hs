-- OOPS. Wrong language.
pow10 x = 10 ^ (length (show x))
ispow2 x = elem x . takeWhile (<=x) $ iterate (*2) 1
terminates x = let g = gcd x (pow10 x) in ispow2 $ pow10 x `div` g
run s = terminates . read . tail $ dropWhile (/='.') s
main = interact (\x -> if run x then "1" else "0")
