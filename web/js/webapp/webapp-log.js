$(document).ready(function () {
 var Territory = Backbone.Model.extend({});

var Territories = Backbone.Collection.extend({
  model: Territory,
  url: "webresources/webapp/data/query.json"
});

var territories = new Territories();

var columns = [{
    name: "id", // The key of the model attribute
    label: "ID", // The name to display in the header
    editable: false, // By default every cell in a column is editable, but *ID* shouldn't be
    // Defines a cell type, and ID is displayed as an integer without the ',' separating 1000s.
    cell: Backgrid.IntegerCell.extend({
      orderSeparator: ''
    })
  }, {
    name: "name",
    label: "Name",
    // The cell type can be a reference of a Backgrid.Cell subclass, any Backgrid.Cell subclass instances like *id* above, or a string
    cell: "string" // This is converted to "StringCell" and a corresponding class in the Backgrid package namespace is looked up
  }];

// Initialize a new Grid instance
var grid = new Backgrid.Grid({
  columns: columns,
  collection: territories
});

// Render the grid and attach the root to your HTML document
$("#log").append(grid.render().el);

// Fetch some countries from the url
territories.fetch({reset: true});

});