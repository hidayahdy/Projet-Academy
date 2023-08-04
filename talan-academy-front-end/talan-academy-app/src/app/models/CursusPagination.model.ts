import { Cursus } from './cursus.model';
export interface CursusPagination {
  content: Cursus[];

  pageable: {
    sort: {
      empty: boolean;

      sorted: boolean;

      unsorted: boolean;
    };

    offset: number;

    pageSize: number;

    pageNumber: number;

    unpaged: boolean;

    paged: boolean;
  };

  last: boolean;

  totalElements: number;

  totalPages: number;

  size: number;

  number: number;

  sort: {
    empty: boolean;

    sorted: boolean;

    unsorted: boolean;
  };

  first: boolean;

  numberOfElements: number;

  empty: boolean;
}
