export enum Types {
  INT = "INT",
  DOUBLE = "DOUBLE",
  STRING = "STRING",
  BOOLEAN = "BOOLEAN",
  FLOAT = "FLOAT",
  LONG = "LONG",
  ENUM = "ENUM",
  ARRAY = "ARRAY",
  LIST = "LIST",
  UNKNOWN = "UNKNOWN",
  CUSTOM = "CUSTOM",
  MAP = "MAP",
  GENERIC = "GENERIC",
  RECURSION_REACHED = "RECURSION_REACHED",
  GENERIC_NO_ANNOTATION = "GENERIC_NO_ANNOTATION",
}

export type GenericTypeJson = {
  className: string
  fieldName: string
  type: Types
  id: string
  value: any
  isShown: boolean
  possibleValues?: string[]
  customValues?: GenericTypeJson[]
}

export type ExtendedType = GenericTypeJson & {
  isValid: boolean
  valueString: string
  newValueString: string
}

export type ChangeJson = {
  id: string
  newValueString: string
}

export type FieldWithValues = Omit<GenericTypeJson, "customValues"> & {
  initialValue: any
  currentValue: any
  customValues?: FieldWithValues[]
}
