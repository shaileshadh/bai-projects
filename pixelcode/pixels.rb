# Benchmarks:
# Small 30kb: 00:00.550 to write, 00:00.490 to read
# Large 3000kb: 00:10.350 to write, 00:10.340 to read

require 'enumerator'
require 'rubygems'
require 'RMagick'
include Magick
def writeimage(outname, str, owid=-1)
	ar=str.enum_for(:each_byte).enum_for(:each_slice, 3).to_a
	if owid==-1 then owid = Math.sqrt(ar.length).ceil end
	ohei = ar.length / owid
	ohei+=1 if ar.length % owid != 0
	outim = Image.new(owid, ohei) {self.background_color="black"}
	xpos, ypos = 0,0
	ar.each do |px|
		outim.pixel_color(xpos,ypos,Pixel.new(px[0], px[1], px[2]))
		xpos += 1
		if xpos >= owid
			ypos += 1
			xpos=0
		end
	end
	outim.write(outname)
end
def readimage(inname)
	img = ImageList.new(inname)
	ar = img.export_pixels.flatten
	str = Array.new(ar.length)
	ar.each_index do |i|
		c=ar[i]
		if c == 0 then break end
		str[i]=c.chr
	end
	str.to_s
end

unless ARGV.length == 2
	puts "invalid args"
	exit
end
case ARGV[0]
	when "write"
		s=""
		file = File.open(ARGV[1])
		writeimage(ARGV[1]+".png", file)
	when "read"
		puts readimage(ARGV[1])
	else
		puts "invalid args"
		exit
end


