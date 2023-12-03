export interface MovieImage {
  id:number;
  imageTypeId: number;
  moveId?: number;
  createDate?: Date;
  fsPath: String;
  fileName: String;
  fileContent: any[];
}
