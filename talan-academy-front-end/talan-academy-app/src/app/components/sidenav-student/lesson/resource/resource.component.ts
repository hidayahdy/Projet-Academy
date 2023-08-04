import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Resource } from 'src/app/models/resource.model';
import { ResourceService } from 'src/app/services/resource.service';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.scss'],
})
export class ResourceComponent implements OnInit {
  resource!: Resource[];
  typesOfShoes: string[] = [
    'Boots',
    'Clogs',
    'Loafers',
    'Moccasins',
    'Sneakers',
  ];
  constructor(
    private activatedRoute: ActivatedRoute,
    private resourceService: ResourceService
  ) {}

  ngOnInit(): void {
    const lessonId = +this.activatedRoute.snapshot.params['id'];
    this.resourceService.getResourceByLessonId(lessonId).subscribe((res) => {
      this.resource = res;
      console.log(this.resource);
    });
  }
}
