import glob
from statistics import *
from brush import *
from coordiante import *
from histogram import *

#===========================================================
def getMask():
    if brushColor == Color.BLUE:
        return blueMasks[ currentImage ]
    elif brushColor == Color.RED:
        return redMasks[ currentImage ]
    elif brushColor == Color.GREEN:
        return greenMasks[ currentImage ]
    elif brushColor == Color.PURPLE:
        return purpleMasks[ currentImage ]
    else:
        return yellowMasks[ currentImage ]
# ===========================================================
def currentHistogram():
    if brushColor == Color.BLUE:
        return blueHistogram
    elif brushColor == Color.RED:
        return redHistogram
    elif brushColor == Color.GREEN:
        return greenHistogram
    elif brushColor == Color.PURPLE:
        return purpleHistogram
    else:
        return yellowHistogram
# ===========================================================
def currentStatistics():
    if brushColor == Color.BLUE:
        return blueStatistics
    elif brushColor == Color.RED:
        return redStatistics
    elif brushColor == Color.GREEN:
        return greenStatistics
    elif brushColor == Color.PURPLE:
        return purpleStatistics
    else:
        return yellowStatistics
# ===========================================================
def computeHistograms():
    global blueHistogram
    global redHistogram
    global greenHistogram
    global purpleHistogram
    global yellowHistogram
    blueHistogram   = computeSpecificColorHistograms( blueMasks, hsvImages   )
    redHistogram    = computeSpecificColorHistograms( redMasks, hsvImages    )
    greenHistogram  = computeSpecificColorHistograms( greenMasks, hsvImages   )
    purpleHistogram = computeSpecificColorHistograms( purpleMasks, hsvImages   )
    yellowHistogram = computeSpecificColorHistograms( yellowMasks, hsvImages )
# ===========================================================
def computeStatistics():
    global blueStatistics
    global redStatistics
    global greenStatistics
    global purpleStatistics
    global yellowStatistics
    blueStatistics.calculate   ( blueHistogram )
    redStatistics.calculate    ( redHistogram )
    greenStatistics.calculate  ( greenHistogram )
    purpleStatistics.calculate ( purpleHistogram )
    yellowStatistics.calculate ( yellowHistogram )
# ===========================================================
def tintMask( mask, color ):
  percentages = color[ 0 ] / 255, color[ 1 ] / 255, color[ 2 ] / 255
  return cv2.multiply( mask, percentages )
# ===========================================================
def updateScreen():
    #make copy of captured image so that we can modify it
    image = images[ currentImage ].copy()

    #compute masks
    currentMask       = getMask()
    currentBlueMask   = blueMasks  [ currentImage ]
    currentRedMask    = redMasks   [ currentImage ]
    currentGreenMask  = greenMasks [ currentImage ]
    currentPurpleMask = purpleMasks[ currentImage ]
    currentYellowMask = yellowMasks[ currentImage ]

    #remove painted regions
    cv2.subtract( image, currentBlueMask,   image )
    cv2.subtract( image, currentRedMask,    image )
    cv2.subtract( image, currentGreenMask,  image )
    cv2.subtract( image, currentPurpleMask, image )
    cv2.subtract( image, currentYellowMask, image )

    #compute tinted masks
    currentColor        = calculateBrushColor( brushColor   )
    blueColor           = calculateBrushColor( Color.BLUE   )
    redColor            = calculateBrushColor( Color.RED    )
    greenColor          = calculateBrushColor( Color.GREEN  )
    purpleColor         = calculateBrushColor( Color.PURPLE )
    yellowColor         = calculateBrushColor( Color.YELLOW )

    tintedMaskBGR       = tintMask( currentMask,       currentColor )
    tintedBlueMaskBGR   = tintMask( currentBlueMask,   blueColor    )
    tintedRedMaskBGR    = tintMask( currentRedMask,    redColor     )
    tintedGreenMaskBGR  = tintMask( currentGreenMask,  greenColor   )
    tintedPurpleMaskBGR = tintMask( currentPurpleMask, purpleColor  )
    tintedYellowMaskBGR = tintMask( currentYellowMask, yellowColor  )

    #draw painted regions on top of image
    cv2.add( image, tintedBlueMaskBGR,   image )
    cv2.add( image, tintedRedMaskBGR,    image )
    cv2.add( image, tintedGreenMaskBGR,  image )
    cv2.add( image, tintedPurpleMaskBGR, image )
    cv2.add( image, tintedYellowMaskBGR, image )

    drawText( image, "Capture with Painted Regions", Alignment.CENTER,0 )

    if brushColor != Color.ERASE:
        drawText( tintedMaskBGR, "Sampled " + colorName( brushColor ) + " Pixels", Alignment.CENTER, 0 )

    #draw brush
    if lastMousePosition != None:
        options           = BrushOptions()
        options.center    = lastMousePosition
        options.size      = brushSize
        options.shape     = brushShape
        options.color     = calculateBrushColor( brushColor )
        options.thickness = calculateBrushThickness( mouseDown )
        drawBrush( image, options )

    desiredHeight, _, _ = image.shape
    histogramImage = drawHistogram( currentHistogram(), currentStatistics(), desiredHeight, brushColor )

    #show image side by side with mask for current color
    painted = np.hstack( [ image, tintedMaskBGR, histogramImage ] )

    cv2.imshow( windowName, painted )
#===========================================================
def mouseCallback(event, x, y, flags, param):
    global lastMousePosition
    global mouseDown

    lastMousePosition = Coordinate(x, y)

    if event == cv2.EVENT_LBUTTONDOWN:
       mouseDown = True

    elif event == cv2.EVENT_LBUTTONUP:
        mouseDown = False

    if mouseDown:
        # compute masks
        currentBlueMask   = blueMasks   [ currentImage ]
        currentRedMask    = redMasks    [ currentImage ]
        currentGreenMask  = greenMasks  [ currentImage ]
        currentPurpleMask = purpleMasks [ currentImage ]
        currentYellowMask = yellowMasks [ currentImage ]

        white = 255, 255, 255
        black = 0, 0, 0

        options           = BrushOptions()
        options.center    = lastMousePosition
        options.size      = brushSize
        options.shape     = brushShape
        options.thickness = calculateBrushThickness( mouseDown )

        options.color = white if brushColor == Color.BLUE else black
        drawBrush( currentBlueMask, options )

        options.color = white if brushColor == Color.RED else black
        drawBrush( currentRedMask, options )

        options.color = white if brushColor == Color.GREEN else black
        drawBrush( currentGreenMask, options )

        options.color = white if brushColor == Color.PURPLE else black
        drawBrush( currentPurpleMask, options )

        options.color = white if brushColor == Color.YELLOW else black
        drawBrush( currentYellowMask, options )

        computeHistograms()
        computeStatistics()

#    print("mouse position: " +str(x)+", " +str(y))
    updateScreen()

#===========================================================
windowName="Joe Paint"
fileNames   =   glob.glob('snapshots/*.png')
images      =   []
hsvImages   =   []

blueMasks   =   []
redMasks    =   []
greenMasks  =   []
purpleMasks =   []
yellowMasks =   []

blueHistogram    = Histogram()
redHistogram     = Histogram()
greenHistogram   = Histogram()
purpleHistogram  = Histogram()
yellowHistogram  = Histogram()

blueStatistics   = ColorChannelsStatistics()
redStatistics    = ColorChannelsStatistics()
greenStatistics  = ColorChannelsStatistics()
purpleStatistics = ColorChannelsStatistics()
yellowStatistics = ColorChannelsStatistics()

for fileName in fileNames:
    loadedImage = cv2.imread( fileName )
    hsvImage = cv2.cvtColor(loadedImage, cv2.COLOR_BGR2HSV)
    images.append( loadedImage )
    hsvImages.append( hsvImage )

    height, width, _ = loadedImage.shape
    mask = np.zeros( ( height, width, 3 ), dtype="uint8" )
    blueMasks.append(mask.copy())
    redMasks.append(mask.copy())
    greenMasks.append(mask.copy())
    purpleMasks.append(mask.copy())
    yellowMasks.append(mask.copy())

currentImage      = 0
lastKeyPressed    = None
lastMousePosition = None

brushShape        = BrushShape.CIRCLE
brushColor        = Color.BLUE
brushSize         = 32
mouseDown         = False

updateScreen()
cv2.setMouseCallback(windowName, mouseCallback)

while True:
    keyPressed = cv2.waitKey(1)

    #spacebar change brush shape
    if keyPressed == 32:
        if brushShape == BrushShape.TRIANGLE:
            brushShape = BrushShape.RECTANGLE
        else:
            brushShape = BrushShape( np.int32( brushShape.value ) + 1 )
        updateScreen()

    if keyPressed == ord( '=' ):
        brushSize = brushSize + 1
        updateScreen()

    if keyPressed == ord( '-' ):
        brushSize = brushSize - 1
        updateScreen()

    #change brush color
    if keyPressed == ord( '1' ):
        brushColor = Color.BLUE
        updateScreen()

    elif keyPressed == ord( '2' ):
        brushColor = Color.RED
        updateScreen()

    elif keyPressed == ord( '3' ):
        brushColor = Color.GREEN
        updateScreen()

    elif keyPressed == ord( '4' ):
        brushColor = Color.PURPLE
        updateScreen()

    elif keyPressed == ord( '5' ):
        brushColor = Color.YELLOW
        updateScreen()

    elif keyPressed == ord( '6' ):
        brushColor = Color.ERASE
        updateScreen()

    # Left Arrow or [
    elif keyPressed== 2 or keyPressed == ord( '[' ):
        currentImage -= 1
        if currentImage < 0:
            currentImage = len( images ) - 1
        updateScreen()

    # Right Arrow or ]
    if keyPressed == 3 or keyPressed == ord( ']' ):
        currentImage += 1
        if currentImage == len( images ):
            currentImage = 0
        updateScreen()

    #Escape or Q exits Joe Paint!!!
    if keyPressed == 27 or keyPressed == ord( 'q' ):
        break

    if keyPressed != lastKeyPressed:
        lastKeyPressed = keyPressed
        if keyPressed!= 255:
            print( keyPressed )
