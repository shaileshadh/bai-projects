--Yup, i'm doing this on a computer.
cubes = takeWhile (<2009) $ map (^3) [2..]
divcubes n = (>=1) . length $ filter (\x -> n `mod` x == 0) cubes
run = length . filter divcubes $ [1..2009]
