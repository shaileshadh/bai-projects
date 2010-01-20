tobase 0 _ = []
tobase x base = let (a,b) = quotRem x base in b : tobase a base
revble n base = let k = tobase n base in k == reverse k
run = sum . filter (\n -> revble n 4 && revble n 6) $ [1..10^7]
