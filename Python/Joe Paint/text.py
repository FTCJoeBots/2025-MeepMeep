
import cv2
import numpy as np
from enum import Enum

#===========================================================
class Alignment( Enum ):
    CENTER = 1,
    LEFT   = 2,
    RIGHT  = 3,
# ===========================================================
def drawText( image, text, alignment, y ):
    fontFace  = cv2.FONT_HERSHEY_DUPLEX
    scale     = .8
    thickness = 1

    ( textWidth, textHeight ), _ = cv2.getTextSize( text, fontFace, scale, thickness )

    _, imageWidth, _ = image.shape
    margin = 12

    if alignment == Alignment.CENTER:
        textX = np.int32( np.round( imageWidth / 2 - textWidth / 2  ) )
    elif alignment == Alignment.LEFT:
        textX = 0
    else:
        textX = imageWidth - textWidth

    textY = textHeight + margin + y
    color = ( 255, 255, 255 )

    cv2.putText( image,
                 text,
                 ( textX, textY ),
                 fontFace,
                 scale,
                 color,
                 thickness,
                 cv2.LINE_AA )
# ===========================================================