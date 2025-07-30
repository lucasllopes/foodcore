package com.fiap.foodcore.infrastructure.web.controller;

import com.fiap.foodcore.application.usecase.*;
import com.fiap.foodcore.application.usecase.input.CreateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.input.UpdateUserSubtypeInput;
import com.fiap.foodcore.application.usecase.output.CreateUserSubtypeOutput;
import com.fiap.foodcore.application.usecase.output.UpdateUserSubtypeOutput;
import com.fiap.foodcore.domain.pagination.DomainPage;
import com.fiap.foodcore.domain.pagination.PageRequestDomain;
import com.fiap.foodcore.domain.pagination.SortOrder;
import com.fiap.foodcore.infrastructure.presenter.UserSubtypePresenter;
import com.fiap.foodcore.infrastructure.web.controller.dto.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subtipos")
public class UserSubtypeControllerImpl implements UserSubtypeController {

    private final static Logger logger = LoggerFactory.getLogger(UserSubtypeControllerImpl.class);

    private final CreateUserSubtypeUseCase createUserSubtype;
    private final UpdateUserSubtypeUseCase updateUserSubtype;
    private final FindUserSubtypeByIdUseCase findUserSubtypeById;
    private final FindUserSubtypeByNameUseCase findUserSubtypeByNameUseCase;
    private final DeleteUserSubtypeUseCase deleteUserSubtype;
    private final ListUserSubtypeUseCase listUserSubtype;


    public UserSubtypeControllerImpl(CreateUserSubtypeUseCase createUserSubtype, UpdateUserSubtypeUseCase updateUserSubtype, FindUserSubtypeByIdUseCase findUserSubtypeById, FindUserSubtypeByNameUseCase findUserSubtypeByNameUseCase, DeleteUserSubtypeUseCase deleteUserSubtype, ListUserSubtypeUseCase listUserSubtype) {
        this.createUserSubtype = createUserSubtype;
        this.updateUserSubtype = updateUserSubtype;
        this.findUserSubtypeById = findUserSubtypeById;
        this.findUserSubtypeByNameUseCase = findUserSubtypeByNameUseCase;
        this.deleteUserSubtype = deleteUserSubtype;
        this.listUserSubtype = listUserSubtype;
    }


    @PostMapping
    public ResponseEntity<UserSubtypeResponseDTO> createUserSubtype(@Valid @RequestBody UserSubtypeRequestDTO dto) {
        logger.info("Handling POST request to /subtipos");

        CreateUserSubtypeInput input = UserSubtypePresenter.toInputCreate(dto);
        CreateUserSubtypeOutput output = createUserSubtype.execute(input);

        UserSubtypeResponseDTO response = UserSubtypePresenter.toDto(output);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserSubtypeResponseDTO> updateUserSubtype(@PathVariable Long id, @Valid @RequestBody UserSubtypeUpdateRequestDTO dto) {
        logger.info("Handling PUT request to /subtipos");

        UpdateUserSubtypeInput input = UserSubtypePresenter.toInputUpdate(dto);
        UpdateUserSubtypeOutput output = updateUserSubtype.execute(id, input);

        UpdateUserSubtypeResponseDTO response = UserSubtypePresenter.toUpdateDto(output);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSubtypeResponseDTO> findById(@PathVariable Long id) {
        logger.info("Handling GET request to /{ID}");

        CreateUserSubtypeOutput output = findUserSubtypeById.execute(id);
        UserSubtypeResponseDTO dto = UserSubtypePresenter.toDto(output);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserSubtypeResponseDTO>> findByName(@PathVariable String name) {
        logger.info("Handling GET request to /subtipos/name/{}", name);

        List<CreateUserSubtypeOutput> outputList = findUserSubtypeByNameUseCase.execute(name);

        List<UserSubtypeResponseDTO> responseList = outputList.stream()
                .map(UserSubtypePresenter::toDto)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSubtype(@PathVariable Long id) {
        logger.info("Handling DELETE request to /subtipos");
        deleteUserSubtype.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_DONO')")
    public ResponseEntity<Page<UserSubtypeResponseDTO>> listUserSubtypePaginated(Pageable pageable){
        logger.info("Handling GET request to /subtipos");
        List<SortOrder> sortOrders = pageable.getSort().stream()
                .map(order -> new SortOrder(order.getProperty(), order.isAscending()))
                .toList();

        PageRequestDomain pr = new PageRequestDomain(pageable.getPageNumber(), pageable.getPageSize(), sortOrders);

        DomainPage<CreateUserSubtypeOutput> outputs = listUserSubtype.execute(pr);
        List<UserSubtypeResponseDTO> dtos = outputs.getItems()
                .stream().map(UserSubtypePresenter::toDto)
                .toList();

        Page<UserSubtypeResponseDTO> paginatedUser = new PageImpl<>(
                dtos,
                pageable,
                outputs.getTotalElements()
        );

        return ResponseEntity.ok(paginatedUser);
    }
}
