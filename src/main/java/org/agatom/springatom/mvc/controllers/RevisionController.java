package org.agatom.springatom.mvc.controllers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.agatom.springatom.data.model.NAbstractAuditable;
import org.agatom.springatom.data.repo.provider.RepositoriesHelper;
import org.agatom.springatom.data.service.services.NAuditableService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionMetadata;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(
  value = "/sa/revisions/"
)
@SuppressWarnings("unchecked")
class RevisionController {
  @Autowired
  private RepositoriesHelper      helper           = null;
  @Autowired
  private NAuditableService       auditableService = null;
  private List<AuditableEndpoint> endpointList     = null;

  @RequestMapping
  public ResponseEntity<List<AuditableEndpoint>> getAuditableEndpoints() {
    return ResponseEntity.ok(this.collectEndpoints());
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "{entity}/{id}")
  public ResponseEntity<?> getAll(@PathVariable("id") final Long id,
                                  @PathVariable("entity") String entity) throws EndpointNotFound {
    final Optional<AuditableEndpoint> any = this.getEndpoint(entity);
    if (any.isPresent()) {
      final Class<NAbstractAuditable> domainType = (Class<NAbstractAuditable>) any.get().getDomainType();
      final Collection<RevisionResource> list = FluentIterable
        .from(this.auditableService.findRevisions(domainType, id))
        .transform(new Function<Revision<?, ?>, RevisionResource>() {
          @Nullable
          @Override
          public RevisionResource apply(final Revision<?, ?> input) {
            final RevisionResource resource = toRevisionResource(input);

            try {
              final String revAsString = input.getRevisionNumber().toString();
              resource.add(linkTo(methodOn(RevisionController.class).getOne(id, Integer.valueOf(revAsString), entity)).withRel(revAsString));
            } catch (EndpointNotFound ignore) {
            }

            return resource;
          }
        })
        .toList();

      final RevisionsResource resource = new RevisionsResource().setResource(list);
      resource.add(linkTo(methodOn(RevisionController.class).getAll(id, entity)).withSelfRel());

      return ResponseEntity.ok(resource);
    }
    throw new EndpointNotFound(String.format("%s is not associated with auditable endpoints", entity));
  }

  @RequestMapping(value = "{entity}/{id}/{revision}")
  public ResponseEntity<?> getOne(@PathVariable("id") final Long id,
                                  @PathVariable("revision") final Integer revision,
                                  @PathVariable("entity") String entity) throws EndpointNotFound {
    final Optional<AuditableEndpoint> any = this.getEndpoint(entity);
    if (any.isPresent()) {
      final Class<NAbstractAuditable> domainType = (Class<NAbstractAuditable>) any.get().getDomainType();
      final RevisionResource resource = this.toRevisionResource(this.auditableService.findInRevision(domainType, id, revision));

      resource.add(linkTo(methodOn(RevisionController.class).getOne(id, revision, entity)).withSelfRel());
      resource.add(linkTo(methodOn(RevisionController.class).getAll(id, entity)).withRel("all"));

      return ResponseEntity.ok(resource);
    }
    throw new EndpointNotFound(String.format("%s is not associated with auditable endpoints", entity));
  }

  private RevisionResource toRevisionResource(final Revision<?, ?> rev) {
    return new RevisionResource(rev);
  }

  private Optional<AuditableEndpoint> getEndpoint(final String entity) {
    return this.collectEndpoints()
      .stream()
      .filter(ae -> ae.getName().equalsIgnoreCase(entity))
      .findAny();
  }

  private List<AuditableEndpoint> collectEndpoints() {
    if (this.endpointList != null) {
      return this.endpointList;
    }
    return this.endpointList = this.helper.getAuditableRepositories()
      .stream()
      .map(ri -> {
        final AuditableEndpoint ae = new AuditableEndpoint();
        final Link selfLink = linkTo(methodOn(RevisionController.class).getAuditableEndpoints()).withSelfRel();

        ae.setName(StringUtils.uncapitalize(ClassUtils.getShortName(ri.getDomainType())));
        ae.setDomainType(ri.getDomainType());

        ae.add(new Link(selfLink.getHref() + String.format("%s/{id}", ae.getName()), "all"));
        ae.add(new Link(selfLink.getHref() + String.format("%s/{id}/{revision}", ae.getName()), "one"));
        ae.add(selfLink);

        return ae;
      })
      .sorted()
      .collect(Collectors.toList());
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested entity is not auditable")
  private static class EndpointNotFound
    extends Exception {
    private static final long serialVersionUID = -6874224245962487215L;

    public EndpointNotFound(final String msg) {
      super(msg);
    }
  }

  private static class RevisionsResource
    extends ResourceSupport {
    private Collection<RevisionResource> resource = null;

    public Collection<RevisionResource> getResource() {
      return resource;
    }

    public RevisionsResource setResource(final Collection<RevisionResource> resource) {
      this.resource = resource;
      return this;
    }
  }

  private static class RevisionResource
    extends ResourceSupport {
    @JsonIgnore
    private final Revision<?, ?> rev;

    public RevisionResource(final Revision<?, ?> rev) {
      this.rev = rev;
    }

    public Number getRevisionNumber() {
      return rev.getRevisionNumber();
    }

    public RevisionMetadata<?> getMetadata() {
      return rev.getMetadata();
    }

    public DateTime getRevisionDate() {
      return rev.getRevisionDate();
    }

    public Object getEntity() {
      return rev.getEntity();
    }
  }

  private static class AuditableEndpoint
    extends ResourceSupport
    implements Comparable<AuditableEndpoint> {
    private Collection<Link> links      = Sets.newHashSetWithExpectedSize(3);
    private String           name       = null;
    @JsonIgnore
    private Class<?>         domainType = null;

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
        .add("links", links)
        .add("name", name)
        .toString();
    }

    public String getName() {
      return name;
    }

    public AuditableEndpoint setName(final String name) {
      this.name = name;
      return this;
    }

    public Class<?> getDomainType() {
      return domainType;
    }

    public AuditableEndpoint setDomainType(final Class<?> domainType) {
      this.domainType = domainType;
      return this;
    }

    @Override
    public int compareTo(final AuditableEndpoint o) {
      return ComparisonChain.start()
        .compare(this.name, o.name)
        .result();
    }
  }
}
