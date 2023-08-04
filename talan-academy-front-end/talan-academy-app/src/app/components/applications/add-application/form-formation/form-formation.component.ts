import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup } from '@angular/forms';

interface Diplomat {
  value: string;
  viewValue: string;
}

interface Speciality {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-form-formation',
  templateUrl: './form-formation.component.html',
  styleUrls: ['./form-formation.component.scss'],
})
export class FormFormationComponent implements OnInit {
  @Output() newItemEvent = new EventEmitter<any>();
  @Output() ItemEvent = new EventEmitter<any>();
  @Input() angForm: FormGroup | any;
  @Input() saveClicked: boolean = false;
  files = [];
  selectedFile!: any;
  Name!: any;
  diplomat: Diplomat[] = [
    { value: 'ENGINEER', viewValue: 'Ingénieur' },
    { value: 'DOCTORATE', viewValue: 'Doctorant' },
    { value: 'OTHER', viewValue: 'Autre' },
  ];
  specialities: Speciality[] = [
    { value: 'ELECTRIQUE', viewValue: 'Génie Electrique' },
    { value: 'HYDRAULICS', viewValue: 'Génie Hydraulique' },
    { value: 'CIVIL', viewValue: 'Génie Civil' },
    { value: 'ENERGY', viewValue: 'Génie Energétique' },
    { value: 'ARCHITECTURE', viewValue: 'Architecture' },
    { value: 'AUTOMATIC', viewValue: 'Automatique' },
    { value: 'MECHANICAL', viewValue: 'Génie Mécanique' },
    { value: 'OTHER', viewValue: 'Autre' },
  ];
  constructor() {}

  ngOnInit(): void {}
  onFileSelected(event: any) {
    this.newItemEvent.emit(event.target.files[0]);
    this.selectedFile = event.target.files[0];
    this.Name = this.selectedFile.name;
    this.files.push(this.Name as never);
  }
}
