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

export interface GenericTypeJson {
  className: string
  fieldName: string
  type: Types
  valueString: string
  id: string
  newValueString: string
  isValid: boolean
  value: any
  isShown: boolean
  possibleValues?: string[]
  customValues?: GenericTypeJson[]
  isOpened?: boolean
}

export type ChangeJson = {
  id: string
  newValueString: string
}
