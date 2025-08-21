enum LightType {
  RGB_INDICATOR = "RGB_INDICATOR",
  HEADLIGHT = "HEADLIGHT",
}

type LightObject =
  | { id: string; type: LightType.RGB_INDICATOR; value: number }
  | { id: string; type: LightType.HEADLIGHT; value: boolean }
