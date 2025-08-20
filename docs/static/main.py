# pip install pillow
from PIL import Image
import os
import math

# --- CONFIG ---
SRC_DIR = "input"          # folder with your PNGs
DST_DIR = "out"            # output folder
TARGET_HEX = "0b0d0d"      # color to replace (background)
# replace with transparent; change if you want a color (R,G,B,A)
REPLACEMENT = (0, 0, 0, 0)
FUZZ = 0.12                # tolerance (0.0 = exact match, 0.10..0.20 typical)
TRIM_ALPHA_THRESHOLD = 1   # pixels with alpha <= this are considered empty for trimming
# 0 = only fully transparent are empty; raise (e.g. 10..30) to trim faint halos

# --- HELPERS ---


def hex_to_rgb(s):
    s = s.strip().lstrip("#")
    if len(s) == 3:  # e.g. 'abc' -> 'aabbcc'
        s = "".join(ch*2 for ch in s)
    if len(s) not in (6, 8):
        raise ValueError(f"Bad hex '{s}'")
    r = int(s[0:2], 16)
    g = int(s[2:4], 16)
    b = int(s[4:6], 16)
    a = int(s[6:8], 16) if len(s) == 8 else 255
    return (r, g, b, a)


def close_enough_rgb(a, b, fuzz):
    # Euclidean distance in RGB space, normalized to [0,1]
    dr, dg, db = a[0]-b[0], a[1]-b[1], a[2]-b[2]
    dist = math.sqrt(dr*dr + dg*dg + db*db)
    return (dist / (math.sqrt(3)*255)) <= fuzz


# --- MAIN ---
os.makedirs(DST_DIR, exist_ok=True)
TARGET = hex_to_rgb(TARGET_HEX)

for name in os.listdir(SRC_DIR):
    if not name.lower().endswith(".png"):
        continue

    src_path = os.path.join(SRC_DIR, name)
    im = Image.open(src_path).convert("RGBA")
    w, h = im.size
    px = im.load()

    # 1) Bucket-fill: replace background-ish pixels with REPLACEMENT
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            # If the pixel is already "empty", leave it
            if a <= TRIM_ALPHA_THRESHOLD:
                continue
            if close_enough_rgb((r, g, b, a), TARGET, FUZZ):
                # keep original alpha if your replacement is a color with alpha=255
                if len(REPLACEMENT) == 4:
                    # if replacement alpha==255 but you want to preserve original alpha, use (R,G,B,a)
                    px[x, y] = REPLACEMENT
                else:
                    px[x, y] = (*REPLACEMENT, a)

    # 2) Trim away fully (or near-fully) transparent borders
    # Build a mask where pixels with alpha > threshold count as content
    alpha = im.getchannel("A")
    # To apply threshold, convert to binary by point mapping
    bin_mask = alpha.point(lambda v: 255 if v >
                           TRIM_ALPHA_THRESHOLD else 0, mode="1")
    bbox = bin_mask.getbbox()
    if bbox:
        im = im.crop(bbox)
    # else: entire image became empty; keep as-is

    out_path = os.path.join(DST_DIR, name)
    im.save(out_path, optimize=True)
    print("Saved:", out_path)
