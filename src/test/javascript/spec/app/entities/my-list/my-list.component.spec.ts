import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { NetflixTestModule } from '../../../test.module';
import { MyListComponent } from 'app/entities/my-list/my-list.component';
import { MyListService } from 'app/entities/my-list/my-list.service';
import { MyList } from 'app/shared/model/my-list.model';

describe('Component Tests', () => {
  describe('MyList Management Component', () => {
    let comp: MyListComponent;
    let fixture: ComponentFixture<MyListComponent>;
    let service: MyListService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [NetflixTestModule],
        declarations: [MyListComponent]
      })
        .overrideTemplate(MyListComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyListComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MyListService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MyList(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.myLists && comp.myLists[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
