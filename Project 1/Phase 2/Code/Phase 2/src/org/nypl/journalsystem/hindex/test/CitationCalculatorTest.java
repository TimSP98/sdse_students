package org.nypl.journalsystem.hindex.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.nypl.journalsystem.hindex.CitationCalculator;
import org.nypl.journalsystem.hindex.ICitationCalculator;
import org.nypl.journalsystem.core.*;
import org.nypl.journalsystem.LibrarySystem;
import org.nypl.journalsystem.Author;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

public class CitationCalculatorTest {
	private ICitationCalculator calculator = new CitationCalculator();
	
	@BeforeEach
	public void setup() {
		calculator = new CitationCalculator();
	}
	
	@AfterEach
	public void tearDown() {
		calculator = null;
	}


	private LibrarySystem lib_setup() throws Exception {
		try {
			LibrarySystem lib_sys = new LibrarySystem();
			lib_sys.load();
			return lib_sys;
		} catch ( Exception e ) {
		 	e.printStackTrace();
			throw new Exception("Failed to load LibrarySystem");
		}

	}
	@Test
	public void invalidParameterIntArray() {
		setup();
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			calculator.calculateHIndex(null);
		}
		);

	}

	@Test
	public void invalidParameterAuthor() {
		Assertions.assertThrows(InvalidParameterException.class, () -> {
			ILibrarySystem lib_sys = new LibrarySystem();
			calculator.calculateHIndex(null, lib_sys);
		}
		);

	}

	@Test
	public void hIndexTest1() throws Exception {
		LibrarySystem lib_sys = lib_setup();
		List<Author> all_authors = lib_sys.getAllAuthors();
		
		int h_index = calculator.calculateHIndex(all_authors.get(0), lib_sys);
		Assertions.assertEquals(0, h_index);





	}

	//TODO: Implement test cases for the citation calculator
}
