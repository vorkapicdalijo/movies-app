import { MovieImage } from './movie-image.model';

export interface Movie {
  id: number;
  title: string;
  description: string;
  movieTypeId: number;
  movieTypeName: string;
  duration: Date;
  releaseDate: Date;
  revenue: number;
  createDate: Date;
  abstractContent: string;

  titleImage: MovieImage;
  detailImages: MovieImage[];
}
