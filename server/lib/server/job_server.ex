defmodule Server.JobServer do
  @moduledoc """
  The JobServer context.
  """

  import Ecto.Query, warn: false
  alias Server.Repo

  alias Server.JobServer.Curriculum

  @doc """
  Returns the list of curriculuns.

  ## Examples

      iex> list_curriculuns()
      [%Curriculum{}, ...]

  """
  def list_curriculuns do
    Repo.all(Curriculum)
  end

  @doc """
  Gets a single curriculum.

  Raises `Ecto.NoResultsError` if the Curriculum does not exist.

  ## Examples

      iex> get_curriculum!(123)
      %Curriculum{}

      iex> get_curriculum!(456)
      ** (Ecto.NoResultsError)

  """
  def get_curriculum!(id), do: Repo.get!(Curriculum, id)

  @doc """
  Creates a curriculum.

  ## Examples

      iex> create_curriculum(%{field: value})
      {:ok, %Curriculum{}}

      iex> create_curriculum(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_curriculum(attrs \\ %{}) do
    %Curriculum{}
    |> Curriculum.changeset(attrs)
    |> Repo.insert()
  end

  @doc """
  Updates a curriculum.

  ## Examples

      iex> update_curriculum(curriculum, %{field: new_value})
      {:ok, %Curriculum{}}

      iex> update_curriculum(curriculum, %{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def update_curriculum(%Curriculum{} = curriculum, attrs) do
    curriculum
    |> Curriculum.changeset(attrs)
    |> Repo.update()
  end

  @doc """
  Deletes a Curriculum.

  ## Examples

      iex> delete_curriculum(curriculum)
      {:ok, %Curriculum{}}

      iex> delete_curriculum(curriculum)
      {:error, %Ecto.Changeset{}}

  """
  def delete_curriculum(%Curriculum{} = curriculum) do
    Repo.delete(curriculum)
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking curriculum changes.

  ## Examples

      iex> change_curriculum(curriculum)
      %Ecto.Changeset{source: %Curriculum{}}

  """
  def change_curriculum(%Curriculum{} = curriculum) do
    Curriculum.changeset(curriculum, %{})
  end

  alias Server.JobServer.JobOpportunity

  @doc """
  Returns the list of opportunities.

  ## Examples

      iex> list_opportunities()
      [%JobOpportunity{}, ...]

  """
  def list_opportunities do
    Repo.all(JobOpportunity)
  end

  @doc """
  Gets a single job_opportunity.

  Raises `Ecto.NoResultsError` if the Job opportunity does not exist.

  ## Examples

      iex> get_job_opportunity!(123)
      %JobOpportunity{}

      iex> get_job_opportunity!(456)
      ** (Ecto.NoResultsError)

  """
  def get_job_opportunity!(id), do: Repo.get!(JobOpportunity, id)

  @doc """
  Creates a job_opportunity.

  ## Examples

      iex> create_job_opportunity(%{field: value})
      {:ok, %JobOpportunity{}}

      iex> create_job_opportunity(%{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def create_job_opportunity(attrs \\ %{}) do
    %JobOpportunity{}
    |> JobOpportunity.changeset(attrs)
    |> Repo.insert()
  end

  @doc """
  Updates a job_opportunity.

  ## Examples

      iex> update_job_opportunity(job_opportunity, %{field: new_value})
      {:ok, %JobOpportunity{}}

      iex> update_job_opportunity(job_opportunity, %{field: bad_value})
      {:error, %Ecto.Changeset{}}

  """
  def update_job_opportunity(%JobOpportunity{} = job_opportunity, attrs) do
    job_opportunity
    |> JobOpportunity.changeset(attrs)
    |> Repo.update()
  end

  @doc """
  Deletes a JobOpportunity.

  ## Examples

      iex> delete_job_opportunity(job_opportunity)
      {:ok, %JobOpportunity{}}

      iex> delete_job_opportunity(job_opportunity)
      {:error, %Ecto.Changeset{}}

  """
  def delete_job_opportunity(%JobOpportunity{} = job_opportunity) do
    Repo.delete(job_opportunity)
  end

  @doc """
  Returns an `%Ecto.Changeset{}` for tracking job_opportunity changes.

  ## Examples

      iex> change_job_opportunity(job_opportunity)
      %Ecto.Changeset{source: %JobOpportunity{}}

  """
  def change_job_opportunity(%JobOpportunity{} = job_opportunity) do
    JobOpportunity.changeset(job_opportunity, %{})
  end
end
