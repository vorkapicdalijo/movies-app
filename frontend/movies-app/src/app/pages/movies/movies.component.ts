import { Component, Input, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import moment from 'moment';
import { Subscription } from 'rxjs';
import { MovieDetailsDialogComponent } from 'src/app/components/dialogs/movie-details-dialog/movie-details-dialog.component';
import { MovieFilter } from 'src/app/models/movie-filter.model';
import { MoviePagination } from 'src/app/models/movie-pagination.model';
import { Movie } from 'src/app/models/movie.model';
import { MoviesService } from 'src/app/services/movies.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MoviesComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];

  public defaultPagination: MoviePagination = {
    offset: 0,
    limit: 10
  };

  public movies: Movie[] = [];
  public moviesPagination: MoviePagination;

  public isLoading: boolean = true;

  public filterForm!: FormGroup;

  public showYearInput: boolean = false;

  public isFilterView: boolean = false;

  public isRevenueFilterActive: boolean = false;
  public isYearFilterActive: boolean = false;

  @Input() max: any = 2023;

  @Input() min: any = 1900;

  @Input() jpCustomFormFieldClass = '';

  constructor(
    private moviesService: MoviesService,
    private dialog: MatDialog,
    private formBuilder: FormBuilder,
  ) { }

  ngOnInit(): void {
    this.filterForm = this.formBuilder.group({
      yearPicker: new FormControl(null, Validators.required)
    });


    let loadSub = this.moviesService.moviesLoad$
      .subscribe((paginationDto: MoviePagination) => {
        this.moviesPagination = paginationDto;
        if (this.isFilterView) {
          this.movies = paginationDto.data;
        }
        else {
          this.movies = [...this.movies, ...paginationDto.data];
        }
        this.isLoading = false;
      });

    this.loadMovies(this.defaultPagination);

    this.subscriptions.push(loadSub);
  }

  public openMovieDetailsDialog(movieId: number) {
    const dialogRef = this.dialog.open(MovieDetailsDialogComponent,
      {
        data: {
          movieId: movieId
        },
        maxWidth: '600px',
        
      });

    dialogRef.afterClosed()
      .subscribe(res => {

      });
  }

  onScroll(e) {
    if (!this.isFilterView) {
      let newPagination: MoviePagination = {
        limit: 10,
        offset: this.moviesPagination.offset + 10
      } 
      this.loadMovies(newPagination);
    }
  }

  public showYearForm() {
    this.showYearInput = !this.showYearInput;
  }

  public checkFormDirty() {
    return this.filterForm.get('yearPicker').dirty;
  }

  public clearForm() {
    this.filterForm.reset({
      yearPicker: null
    });

    this.removeFilters();

    this.loadMovies(this.defaultPagination);
  }

  public getMoviesByYearRevenue() {
    this.isYearFilterActive = true;
    this.isRevenueFilterActive = false;

    let filter: MovieFilter = {
      getTop10MoviesByRevenueByYear : true,
      filterYear: moment(this.filterForm.get('yearPicker')?.value).year(),
      getTop10MoviesByRevenue: false
    }

    this.moviesService.setFilters(filter);
    this.isFilterView = true;

    this.loadMovies(this.defaultPagination);

    this.showYearInput = false;
  }

  public getMoviesByRevenue() {
    this.isRevenueFilterActive = true;
    this.isYearFilterActive = false;

    let filter: MovieFilter = {
      getTop10MoviesByRevenue: true,
      filterYear: null,
      getTop10MoviesByRevenueByYear: null
    }

    this.moviesService.setFilters(filter);
    this.isFilterView = true;

    this.loadMovies(this.defaultPagination);
  }

  public removeFilters() {
    this.movies = [];
    this.moviesService.setFilters(null);

    this.isRevenueFilterActive = false;
    this.isYearFilterActive = false;

    this.showYearInput = false;
    this.isFilterView = false;
    this.loadMovies(this.defaultPagination);
  }

  private loadMovies(pagination: MoviePagination) {
      this.moviesService.getMoviesByPagination(pagination);
  }



  ngOnDestroy(): void {
    this.moviesService.ngOnDestroy();
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    });
  }
}
