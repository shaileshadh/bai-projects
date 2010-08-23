l1 = gets.to_f
l2 = gets.to_f
a = l1**(1.0/6)
b = l2**(1.0/6)
puts b.to_s.to_f.floor - a.to_s.to_f.ceil + 1
