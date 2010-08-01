--A002898
s = [0,0,6,12,90,360,2040,10080,54810,290640,1588356,8676360,47977776,266378112,1488801600]
main = interact (unlines . map l . tail . lines) where
  l r = show $ s !! (read r)
