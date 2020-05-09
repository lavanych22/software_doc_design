import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NetflixTestModule } from '../../../test.module';
import { SavedSearchComponent } from 'app/entities/saved-search/saved-search.component';
import { SavedSearchService } from 'app/entities/saved-search/saved-search.service';
import { SavedSearch } from 'app/shared/model/saved-search.model';

describe('Component Tests', () => {
  describe('SavedSearch Management Component', () => {
    let comp: SavedSearchComponent;
    let fixture: ComponentFixture<SavedSearchComponent>;
    let service: SavedSearchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NetflixTestModule],
        declarations: [SavedSearchComponent]
      })
        .overrideTemplate(SavedSearchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SavedSearchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SavedSearchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SavedSearch(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.savedSearches && comp.savedSearches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
