package view.model.builder;

import view.model.BookDTO;

public class BookDTOBuilder {
    private BookDTO bookDTO;
    public BookDTOBuilder()
    {
        bookDTO= new BookDTO();
    }


    public BookDTOBuilder setAuthor(String author)
    {
        bookDTO.setAuthor(author);
        return this;
    }

    public BookDTOBuilder setStock(int stock)
    {
        bookDTO.setStock(stock);
        return this;
    }
    public BookDTOBuilder setTitle(String title)
    {
        bookDTO.setTitle(title);
        return this;
    }
    public BookDTOBuilder setPrice(float price)
    {
        bookDTO.setPrice(price);
        return this;
    }

  /*  public BookDTOBuilder setQuantity(int quantity)
    {
        bookDTO.setQuantity(quantity);
        return this;
    }*/

    public BookDTO build()
    {
        return bookDTO;
    }
}
